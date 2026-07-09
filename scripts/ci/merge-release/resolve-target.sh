#!/usr/bin/env bash
# Finds the branch a release should be merged into: the next release branch
# in version order, unless TARGET_OVERRIDE names one explicitly. Also derives
# the name of the intermediate merge branch.
#
# Required environment variables:
#   SOURCE_BRANCH   the release branch to merge (e.g. release/3.87)
# Optional environment variables:
#   TARGET_OVERRIDE explicit target branch
#
# Writes `skip`, `target-branch` and `merge-branch` to $GITHUB_OUTPUT.

set -euo pipefail

: "${SOURCE_BRANCH:?SOURCE_BRANCH must be set}"

MERGE_TICKET="TRX-120"

main() {
    local target
    if is_release_branch "$SOURCE_BRANCH"; then
        target="${TARGET_OVERRIDE:-$(next_release_branch)}"
    elif is_epic_or_main_branch "$SOURCE_BRANCH"; then
        ensure_target_was_given
        target="$TARGET_OVERRIDE"
    else
        echo "Source '$SOURCE_BRANCH' is not a release/X.YY, epic/*, master or main branch" >&2
        exit 1
    fi

    if [[ -n "$target" ]]; then
        report_target "$target"
    else
        report_nothing_to_do
    fi
}

is_release_branch() {
    [[ "$1" =~ ^release/[0-9]+\.[0-9]+$ ]]
}

is_epic_or_main_branch() {
    [[ "$1" == epic/* || "$1" == master || "$1" == main ]]
}

ensure_target_was_given() {
    if [[ -z "${TARGET_OVERRIDE:-}" ]]; then
        echo "Source '$SOURCE_BRANCH' has no natural next branch, an explicit target-branch is required" >&2
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
        && [[ "$(printf '%s\n%s\n' "$source_version" "$version" | sort -V | head -n1)" == "$source_version" ]]
}

report_target() {
    local target="$1"
    {
        echo "skip=false"
        echo "target-branch=$target"
        echo "merge-branch=infra/$MERGE_TICKET-merge-$(as_slug "$SOURCE_BRANCH")-into-$(as_slug "$target")"
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
