#!/usr/bin/env bash
# Resolves the branch a merge should target, from the choice made in the
# workflow dialog, and derives the name of the intermediate merge branch.
#
# Required environment variables:
#   SOURCE_BRANCH   the branch to merge (the "Use workflow from" ref)
#   TARGET_MODE     the dropdown choice: "next release ...", "main" or "custom ..."
# Optional environment variables:
#   CUSTOM_TARGET   the target branch, read only when TARGET_MODE is "custom"
#
# Writes `skip`, `target-branch` and `merge-branch` to $GITHUB_OUTPUT.

set -euo pipefail

: "${SOURCE_BRANCH:?SOURCE_BRANCH must be set}"
: "${TARGET_MODE:?TARGET_MODE must be set}"

MERGE_TICKET="TRX-120"

main() {
    ensure_source_is_supported
    case "$TARGET_MODE" in
        main) finish_with_target "main" ;;
        custom*) resolve_custom_target ;;
        *) resolve_next_release ;;
    esac
}

ensure_source_is_supported() {
    is_release_branch "$SOURCE_BRANCH" && return
    is_epic_or_main_branch "$SOURCE_BRANCH" && return
    echo "Source '$SOURCE_BRANCH' is not a release/X.YY, epic/*," \
        "master or main branch" >&2
    exit 1
}

resolve_custom_target() {
    if [[ -z "${CUSTOM_TARGET:-}" ]]; then
        echo "Target 'custom' was chosen but custom-target is empty" >&2
        exit 1
    fi
    finish_with_target "$CUSTOM_TARGET"
}

resolve_next_release() {
    if ! is_release_branch "$SOURCE_BRANCH"; then
        echo "'$SOURCE_BRANCH' has no next release; pick main or custom" >&2
        exit 1
    fi
    local target
    target=$(next_release_branch)
    if [[ -z "$target" ]]; then
        report_nothing_to_do
        return
    fi
    finish_with_target "$target"
}

finish_with_target() {
    local target="$1"
    ensure_target_exists "$target"
    report_target "$target"
}

is_release_branch() {
    [[ "$1" =~ ^release/[0-9]+\.[0-9]+$ ]]
}

is_epic_or_main_branch() {
    [[ "$1" == epic/* || "$1" == master || "$1" == main ]]
}

ensure_target_exists() {
    local target="$1"
    if ! git ls-remote --exit-code --heads origin "$target" >/dev/null 2>&1; then
        echo "Target '$target' is not a branch on origin" >&2
        exit 1
    fi
}

next_release_branch() {
    local version
    while read -r version; do
        if is_newer_than_source "$version"; then
            echo "release/$version"
            return
        fi
    done < <(all_release_versions)
}

all_release_versions() {
    git ls-remote --heads origin 'refs/heads/release/*' \
        | sed 's|.*refs/heads/release/||' \
        | grep -E '^[0-9]+\.[0-9]+$' \
        | sort -V
}

is_newer_than_source() {
    local version="$1" source_version="${SOURCE_BRANCH#release/}"
    [[ "$version" != "$source_version" ]] \
        && is_higher_version "$source_version" "$version"
}

is_higher_version() {
    local lower="$1" higher="$2"
    [[ "$(printf '%s\n%s\n' "$lower" "$higher" | sort -V | head -n1)" == "$lower" ]]
}

report_target() {
    local target="$1"
    local source_slug target_slug
    source_slug=$(as_slug "$SOURCE_BRANCH")
    target_slug=$(as_slug "$target")
    {
        echo "skip=false"
        echo "target-branch=$target"
        echo "merge-branch=infra/$MERGE_TICKET-merge-$source_slug-into-$target_slug"
    } >> "$GITHUB_OUTPUT"
    echo "Resolved target for $SOURCE_BRANCH: $target"
}

report_nothing_to_do() {
    echo "skip=true" >> "$GITHUB_OUTPUT"
    echo "No release branch newer than $SOURCE_BRANCH, skipping"
}

as_slug() {
    printf '%s' "$1" | tr './' '--'
}

main
