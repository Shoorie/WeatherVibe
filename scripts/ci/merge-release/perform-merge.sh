#!/usr/bin/env bash
# Merges SOURCE_BRANCH into MERGE_BRANCH (created from TARGET_BRANCH when it
# does not exist yet) and pushes the result, so a PR from MERGE_BRANCH into
# TARGET_BRANCH can pick it up.
#
# Conflicts on files owned by each release branch (version.txt,
# dependencies.gradle.kts) and on submodule pointers are resolved
# automatically in favour of the target. Any other conflict aborts the merge
# and is left for a human.
#
# Required environment variables:
#   SOURCE_BRANCH   the branch being merged (e.g. release/3.87)
#   TARGET_BRANCH   the branch that will receive the PR (e.g. release/3.88)
#   MERGE_BRANCH    intermediate infra branch carrying the merge commit
# Optional environment variables (default to the github-actions[bot] identity):
#   GIT_USER_NAME, GIT_USER_EMAIL
#
# Writes to $GITHUB_OUTPUT:
#   status          merged / up-to-date / conflict
#   conflict-files  space-separated, empty unless status=conflict
#   auto-resolved   space-separated paths resolved in favour of the target

set -euo pipefail

: "${SOURCE_BRANCH:?SOURCE_BRANCH must be set}"
: "${TARGET_BRANCH:?TARGET_BRANCH must be set}"
: "${MERGE_BRANCH:?MERGE_BRANCH must be set}"

resolved_paths=""
conflicted_paths=""

main() {
    use_bot_identity
    fetch_branch "$SOURCE_BRANCH"
    fetch_branch "$TARGET_BRANCH"

    if already_contains_source "origin/$TARGET_BRANCH"; then
        report "up-to-date"
        return
    fi

    check_out_merge_branch
    if already_contains_source "$MERGE_BRANCH"; then
        report "up-to-date"
        return
    fi

    if merge_source_into_merge_branch; then
        push_merge_branch
        report "merged"
    else
        push_merge_branch
        report "conflict"
    fi
}

use_bot_identity() {
    git config user.name "${GIT_USER_NAME:-github-actions[bot]}"
    git config user.email "${GIT_USER_EMAIL:-41898282+github-actions[bot]@users.noreply.github.com}"
}

fetch_branch() {
    git fetch origin "+refs/heads/$1:refs/remotes/origin/$1"
}

already_contains_source() {
    git merge-base --is-ancestor "origin/$SOURCE_BRANCH" "$1"
}

check_out_merge_branch() {
    if git ls-remote --exit-code --heads origin "$MERGE_BRANCH" >/dev/null; then
        fetch_branch "$MERGE_BRANCH"
        git checkout -B "$MERGE_BRANCH" "origin/$MERGE_BRANCH"
    else
        git checkout -B "$MERGE_BRANCH" "origin/$TARGET_BRANCH"
    fi
}

merge_source_into_merge_branch() {
    git merge --no-ff -m "Merge branch '$SOURCE_BRANCH' into $MERGE_BRANCH" "origin/$SOURCE_BRANCH" \
        && return 0

    resolve_expected_conflicts
    if [[ -n "$conflicted_paths" ]]; then
        git merge --abort
        resolved_paths=""
        return 1
    fi
    git commit --no-edit
}

resolve_expected_conflicts() {
    local path
    while IFS= read -r path; do
        if resolve_keeping_target_side "$path"; then
            resolved_paths="${resolved_paths:+$resolved_paths }$path"
        else
            conflicted_paths="${conflicted_paths:+$conflicted_paths }$path"
        fi
    done < <(git diff --name-only --diff-filter=U)
}

resolve_keeping_target_side() {
    local path="$1"
    if is_submodule_pointer "$path"; then
        keep_target_submodule_revision "$path"
    elif is_owned_by_each_release "$path"; then
        keep_target_side_of_conflicting_lines "$path"
    else
        return 1
    fi
}

is_submodule_pointer() {
    git ls-files -u -- "$1" | grep -q '^160000 '
}

is_owned_by_each_release() {
    case "$1" in
        version.txt | dependencies.gradle.kts) return 0 ;;
        *) return 1 ;;
    esac
}

# The checkout on CI has no initialised submodules, so the pointer is
# resolved purely in the index and the worktree stays untouched.
keep_target_submodule_revision() {
    local path="$1" target_entry
    target_entry=$(git ls-files -u -- "$path" | awk '$3 == "2" { print $1 "," $2; exit }')
    [[ -n "$target_entry" ]] || return 1
    git update-index --cacheinfo "$target_entry,$path"
}

# Re-merges a single file taking only the conflicting lines from the target,
# so non-conflicting changes from the source survive in the same file.
keep_target_side_of_conflicting_lines() {
    local path="$1" base ours theirs
    base=$(mktemp) && ours=$(mktemp) && theirs=$(mktemp)
    git show ":1:$path" > "$base" 2>/dev/null || : > "$base"
    git show ":2:$path" > "$ours" || return 1
    git show ":3:$path" > "$theirs" || return 1
    git merge-file --ours "$ours" "$base" "$theirs" || true
    cp "$ours" "$path"
    git add "$path"
    rm -f "$base" "$ours" "$theirs"
}

push_merge_branch() {
    git push origin "HEAD:refs/heads/$MERGE_BRANCH"
}

report() {
    {
        echo "status=$1"
        echo "conflict-files=$conflicted_paths"
        echo "auto-resolved=$resolved_paths"
    } >> "$GITHUB_OUTPUT"
    echo "Merge of $SOURCE_BRANCH into $TARGET_BRANCH: $1"
}

main
