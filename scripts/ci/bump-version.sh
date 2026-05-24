#!/usr/bin/env bash
set -euo pipefail

VERSION_FILE="version.txt"
RELEASED_VERSION="${RELEASED_VERSION:?RELEASED_VERSION is required}"
GIT_USER_NAME="${GIT_USER_NAME:-github-actions[bot]}"
GIT_USER_EMAIL="${GIT_USER_EMAIL:-41898282+github-actions[bot]@users.noreply.github.com}"

CURRENT=$(tr -d '[:space:]' < "$VERSION_FILE")

MAJOR=${CURRENT%%.*}
REMAINDER=${CURRENT#*.}
MINOR=${REMAINDER%%.*}
PATCH=${REMAINDER#*.}

NEXT_PATCH=$(( PATCH + 1 ))
NEXT_NAME="${MAJOR}.${MINOR}.${NEXT_PATCH}"
NEXT_CODE=$(( MAJOR * 1000000 + MINOR * 1000 + NEXT_PATCH ))

{
  echo "next-name=$NEXT_NAME"
  echo "next-code=$NEXT_CODE"
} >> "$GITHUB_OUTPUT"

printf '%s\n' "$NEXT_NAME" > "$VERSION_FILE"

git config user.name "$GIT_USER_NAME"
git config user.email "$GIT_USER_EMAIL"

git add "$VERSION_FILE"

if git diff --cached --quiet; then
  echo "Nothing to commit — $VERSION_FILE already at $NEXT_NAME"
else
  git commit -m "Change version after v${RELEASED_VERSION} release to v${NEXT_NAME}"
  git push origin "HEAD:${GITHUB_REF_NAME}"
fi

echo "Bumped $CURRENT -> $NEXT_NAME (code $NEXT_CODE)"
