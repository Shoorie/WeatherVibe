#!/usr/bin/env bash
# Builds the Slack payload for the conflict notification and writes it to
# conflict-notification.json in the workspace. A script rather than an
# inline payload, because the conflicted files are listed one per line and
# that is easier to keep readable with jq than inside workflow YAML.
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

ask_line="Rzucisz na to okiem?"
if [[ -n "${MENTION:-}" ]]; then
    ask_line="$MENTION, rzucisz na to okiem?"
fi

intro=$(printf ':warning: *Merge `%s` do `%s` zatrzymał się na konfliktach*\n\n%s' \
    "$SOURCE_BRANCH" "$TARGET_BRANCH" "$ask_line")
files=$(printf '*Pliki w konflikcie:*\n```\n%s\n```' "$(tr ' ' '\n' <<< "$CONFLICT_FILES")")
hint=$(printf '*Co dalej:*\n> 1. `git checkout %s`\n> 2. Zmerguj `origin/%s` i rozwiąż konflikty\n> 3. Wystaw PR do `%s`' \
    "$MERGE_BRANCH" "$SOURCE_BRANCH" "$TARGET_BRANCH")
footer="<$RUN_URL|Zobacz przebieg> • Release Merge Forward"

jq -n \
    --arg summary "Merge $SOURCE_BRANCH do $TARGET_BRANCH zatrzymał się na konfliktach" \
    --arg intro "$intro" \
    --arg files "$files" \
    --arg hint "$hint" \
    --arg footer "$footer" \
    '{
        text: $summary,
        blocks: [
            { type: "section", text: { type: "mrkdwn", text: $intro } },
            { type: "section", text: { type: "mrkdwn", text: $files } },
            { type: "section", text: { type: "mrkdwn", text: $hint } },
            { type: "context", elements: [{ type: "mrkdwn", text: $footer }] }
        ]
    }' > conflict-notification.json

echo "Wrote conflict-notification.json"
