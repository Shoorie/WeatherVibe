#!/usr/bin/env bash
# Opens the merge pull request for MERGE_BRANCH into TARGET_BRANCH, unless
# one is already open. With AUTOMERGE=true the pull request gets the
# automerge label and merges itself once the checks pass; otherwise it goes
# through regular review.
#
# Required environment variables:
#   SOURCE_BRANCH   branch being merged (e.g. release/3.87)
#   TARGET_BRANCH   pull request base (e.g. release/3.88)
#   MERGE_BRANCH    pull request head
#   JIRA_BASE_URL   Jira browse URL
#   AUTOMERGE       "true" adds the automerge label
#   GH_TOKEN        token used by the gh CLI
# Optional environment variables:
#   AUTO_RESOLVED   paths whose conflicts were resolved in favour of the target

set -euo pipefail

: "${SOURCE_BRANCH:?SOURCE_BRANCH must be set}"
: "${TARGET_BRANCH:?TARGET_BRANCH must be set}"
: "${MERGE_BRANCH:?MERGE_BRANCH must be set}"
: "${JIRA_BASE_URL:?JIRA_BASE_URL must be set}"
: "${AUTOMERGE:?AUTOMERGE must be set}"

LINKED_TICKETS=("MOPS-139" "TRX-120")

main() {
    local existing_url
    existing_url=$(open_pull_request_url)
    if [[ -n "$existing_url" ]]; then
        echo "Pull request already open: $existing_url"
        return
    fi
    create_pull_request
}

open_pull_request_url() {
    gh pr list --head "$MERGE_BRANCH" --base "$TARGET_BRANCH" \
        --state open --json url --jq '.[0].url // empty'
}

create_pull_request() {
    gh pr create --head "$MERGE_BRANCH" --base "$TARGET_BRANCH" \
        --title "$(ticket_prefix) Merge $SOURCE_BRANCH into $TARGET_BRANCH" \
        --body "$(pull_request_body)" \
        $(automerge_label)
}

ticket_prefix() {
    local ticket prefix=""
    for ticket in "${LINKED_TICKETS[@]}"; do
        prefix="$prefix[$ticket]"
    done
    echo "$prefix"
}

automerge_label() {
    [[ "$AUTOMERGE" == "true" ]] && echo "--label automerge"
}

pull_request_body() {
    cat <<EOF
Automated merge of \`$SOURCE_BRANCH\` into \`$TARGET_BRANCH\`.

Jira: $(jira_links)
$(resolution_note)

Merge with a merge commit, not squash, so the shared branch history is
preserved and the same conflicts do not come back on the next merge.
EOF
}

jira_links() {
    local ticket links=""
    for ticket in "${LINKED_TICKETS[@]}"; do
        links="${links:+$links, }[$ticket]($JIRA_BASE_URL/$ticket)"
    done
    echo "$links"
}

resolution_note() {
    [[ -n "${AUTO_RESOLVED:-}" ]] || return 0
    echo
    echo "Conflicts resolved automatically, keeping the \`$TARGET_BRANCH\` side of: ${AUTO_RESOLVED// /, }"
}

main
