#!/usr/bin/env bash
# Post or update a sticky coverage comment on the current pull request.
#
# Expects these environment variables:
#   GH_TOKEN    — token with pull-requests:write
#   REPO        — owner/name
#   PR_NUMBER   — pull request number
#
# Looks up a previous comment marked with a hidden HTML marker and rewrites it
# in place so each push replaces the existing comment instead of stacking new
# ones. Falls back to creating a new comment when none exists yet.

set -euo pipefail

marker="<!-- kover-coverage-report -->"
body=$(printf '%s\n\n%s' "$marker" "$(cat kover-summary.md)")

existing_id=$(gh api "repos/$REPO/issues/$PR_NUMBER/comments" \
  --jq "map(select(.body | startswith(\"$marker\"))) | .[0].id // empty")

if [ -n "$existing_id" ]; then
  gh api "repos/$REPO/issues/comments/$existing_id" \
    -X PATCH -f body="$body" > /dev/null
  echo "Updated existing coverage comment $existing_id"
else
  gh pr comment "$PR_NUMBER" --repo "$REPO" --body "$body"
  echo "Posted new coverage comment"
fi
