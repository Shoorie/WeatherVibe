#!/usr/bin/env bash
set -euo pipefail

OUTPUT_FILE="${1:-release-notes.txt}"
MAX_CHARS=14000
TRUNCATION_SUFFIX=$'\n\n... (truncated, see full notes on GitHub)'

PREVIOUS_TAG=$(git tag --sort=-v:refname | sed -n '2p')

if [ -n "$PREVIOUS_TAG" ]; then
  NOTES=$(git log "$PREVIOUS_TAG"..HEAD --pretty=format:"- %s" --no-merges)
else
  NOTES=$(git log --pretty=format:"- %s" --no-merges)
fi

if (( ${#NOTES} > MAX_CHARS )); then
  KEEP=$(( MAX_CHARS - ${#TRUNCATION_SUFFIX} ))
  NOTES="${NOTES:0:$KEEP}${TRUNCATION_SUFFIX}"
fi

printf '%s' "$NOTES" > "$OUTPUT_FILE"

echo "Release notes written to $OUTPUT_FILE (${#NOTES} chars)"
