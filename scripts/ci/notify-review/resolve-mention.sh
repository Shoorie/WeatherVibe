#!/usr/bin/env bash
set -euo pipefail

readonly REVIEWERS_FILE=".github/reviewers.yml"
readonly PLACEHOLDER="REPLACE_WITH_SLACK_MEMBER_ID"

slack_id=$(grep -E "^${PR_AUTHOR}:" "$REVIEWERS_FILE" 2>/dev/null \
  | head -n1 \
  | sed -E 's/^[^:]+:[[:space:]]*//' \
  | tr -d '"' \
  || true)

if [ -n "$slack_id" ] && [ "$slack_id" != "$PLACEHOLDER" ]; then
  mention="<@${slack_id}>"
else
  mention="@${PR_AUTHOR}"
fi

echo "value=${mention}" >> "$GITHUB_OUTPUT"
