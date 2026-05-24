#!/usr/bin/env bash
set -euo pipefail

VERSION_FILE="version.txt"

if [[ ! -f "$VERSION_FILE" ]]; then
  echo "::error::Missing $VERSION_FILE in repository root"
  exit 1
fi

VERSION_NAME=$(tr -d '[:space:]' < "$VERSION_FILE")

if [[ ! "$VERSION_NAME" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
  echo "::error::Invalid version name in $VERSION_FILE: '$VERSION_NAME'. Expected X.Y.Z."
  exit 1
fi

MAJOR=${VERSION_NAME%%.*}
REMAINDER=${VERSION_NAME#*.}
MINOR=${REMAINDER%%.*}
PATCH=${REMAINDER#*.}

VERSION_CODE=$(( MAJOR * 1000000 + MINOR * 1000 + PATCH ))

{
  echo "name=$VERSION_NAME"
  echo "code=$VERSION_CODE"
} >> "$GITHUB_OUTPUT"

echo "Version name: $VERSION_NAME"
echo "Version code: $VERSION_CODE"
