#!/usr/bin/env bash
# Builds the Slack payload for the conflict notification and writes it to
# conflict-notification.json in the workspace.
#
# Required environment variables:
#   SOURCE_BRANCH   branch that was being merged (e.g. release/3.87)
#   TARGET_BRANCH   branch the merge was aimed at (e.g. release/3.88)
#   MERGE_BRANCH    intermediate infra branch, already pushed
#   CONFLICT_FILES  space-separated conflicted paths
#   RUN_URL         link to the workflow run
# Optional environment variables:
#   MENTION         Slack mention of the person who started the workflow

set -euo pipefail

: "${SOURCE_BRANCH:?SOURCE_BRANCH must be set}"
: "${TARGET_BRANCH:?TARGET_BRANCH must be set}"
: "${MERGE_BRANCH:?MERGE_BRANCH must be set}"
: "${CONFLICT_FILES:?CONFLICT_FILES must be set}"
: "${RUN_URL:?RUN_URL must be set}"

main() {
    write_payload > conflict-notification.json
    echo "Wrote conflict-notification.json"
}

write_payload() {
    jq -n \
        --arg summary "$(summary_line)" \
        --arg intro "$(intro_section)" \
        --arg files "$(files_section)" \
        --arg steps "$(steps_section)" \
        --arg footer "$(footer_line)" \
        '{
            text: $summary,
            blocks: [
                { type: "section", text: { type: "mrkdwn", text: $intro } },
                { type: "section", text: { type: "mrkdwn", text: $files } },
                { type: "section", text: { type: "mrkdwn", text: $steps } },
                { type: "context", elements: [{ type: "mrkdwn", text: $footer }] }
            ]
        }'
}

summary_line() {
    echo "Merge $SOURCE_BRANCH do $TARGET_BRANCH zatrzymał się na konfliktach"
}

intro_section() {
    printf ':warning: *Merge `%s` do `%s` zatrzymał się na konfliktach*\n\n%s' \
        "$SOURCE_BRANCH" "$TARGET_BRANCH" "$(ask_line)"
}

ask_line() {
    if [[ -n "${MENTION:-}" ]]; then
        echo "$MENTION, rzucisz na to okiem?"
    else
        echo "Rzucisz na to okiem?"
    fi
}

files_section() {
    printf '*Pliki w konflikcie:*\n```\n%s\n```' "$(one_path_per_line)"
}

one_path_per_line() {
    tr ' ' '\n' <<< "$CONFLICT_FILES"
}

steps_section() {
    local checkout_step="> 1. \`git checkout $MERGE_BRANCH\`"
    local merge_step="> 2. Zmerguj \`origin/$SOURCE_BRANCH\` i rozwiąż konflikty"
    local pr_step="> 3. Wystaw PR do \`$TARGET_BRANCH\`"
    printf '*Co dalej:*\n%s\n%s\n%s' "$checkout_step" "$merge_step" "$pr_step"
}

footer_line() {
    echo "<$RUN_URL|Zobacz przebieg> • Release Merge Forward"
}

main
