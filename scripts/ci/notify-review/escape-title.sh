#!/usr/bin/env bash
set -euo pipefail

escaped=$(printf '%s' "$PR_TITLE" | sed -e 's/\\/\\\\/g' -e 's/"/\\"/g')

echo "value=${escaped}" >> "$GITHUB_OUTPUT"
