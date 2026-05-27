#!/usr/bin/env bash
set -euo pipefail

readonly TICKET_PATTERN='[A-Z]+-[0-9]+'

ticket=$(printf '%s' "$PR_TITLE" | grep -oE "$TICKET_PATTERN" | head -n1 || true)

if [ -z "$ticket" ]; then
  ticket="${FALLBACK_TICKET_PREFIX}${PR_NUMBER}"
fi

{
  echo "id=${ticket}"
  echo "url=${JIRA_BASE_URL}/${ticket}"
} >> "$GITHUB_OUTPUT"
