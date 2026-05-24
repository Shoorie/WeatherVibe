#!/usr/bin/env bash
set -euo pipefail

VERSION="${VERSION:?VERSION is required}"
CHANGELOG="${CHANGELOG:-}"
TAG="v${VERSION}"

if git rev-parse "$TAG" >/dev/null 2>&1; then
  echo "Tag $TAG already exists — skipping"
  echo "created=false" >> "$GITHUB_OUTPUT"
  echo "tag=$TAG" >> "$GITHUB_OUTPUT"
  exit 0
fi

git config user.name "github-actions[bot]"
git config user.email "41898282+github-actions[bot]@users.noreply.github.com"

if [[ -n "$CHANGELOG" ]]; then
  git tag -a "$TAG" -m "$TAG" -m "$CHANGELOG"
else
  git tag -a "$TAG" -m "$TAG"
fi

git push origin "$TAG"

{
  echo "created=true"
  echo "tag=$TAG"
} >> "$GITHUB_OUTPUT"

echo "Created tag $TAG"
