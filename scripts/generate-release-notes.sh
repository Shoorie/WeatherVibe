#!/usr/bin/env bash
set -euo pipefail

OUTPUT_FILE="${1:-release-notes.txt}"

PREVIOUS_TAG=$(git tag --sort=-v:refname | sed -n '2p')

if [ -n "$PREVIOUS_TAG" ]; then
  git log "$PREVIOUS_TAG"..HEAD --pretty=format:"- %s" --no-merges > "$OUTPUT_FILE"
else
  git log --pretty=format:"- %s" --no-merges > "$OUTPUT_FILE"
fi

echo "Release notes written to $OUTPUT_FILE"
