#!/usr/bin/env bash
set -euo pipefail

TAG_PATTERN="${TAG_PATTERN:-v*}"
MAX_SHORT_LENGTH="${MAX_SHORT_LENGTH:-15000}"
RELEASE_VERSION="${RELEASE_VERSION:-}"

PREVIOUS_TAG=$(git tag --list "$TAG_PATTERN" --sort=-v:refname | head -n 1 || true)

if [[ -n "$PREVIOUS_TAG" ]]; then
  RANGE="${PREVIOUS_TAG}..HEAD"
else
  RANGE="HEAD"
fi

CHANGELOG_FULL=$(git log "$RANGE" --pretty=format:"- %s" --no-merges)

if [[ -z "$CHANGELOG_FULL" ]]; then
  CHANGELOG_FULL="- (no changes since ${PREVIOUS_TAG:-initial commit})"
fi

if (( ${#CHANGELOG_FULL} <= MAX_SHORT_LENGTH )); then
  CHANGELOG_SHORT="$CHANGELOG_FULL"
else
  if [[ -n "$RELEASE_VERSION" ]]; then
    FOOTER=$'\n\n_More commits in the v'"$RELEASE_VERSION"$' GitHub Release._'
  else
    FOOTER=$'\n\n_More commits in the GitHub Release._'
  fi

  BUDGET=$(( MAX_SHORT_LENGTH - ${#FOOTER} ))
  TRUNCATED="${CHANGELOG_FULL:0:$BUDGET}"
  TRUNCATED="${TRUNCATED%$'\n'*}"
  CHANGELOG_SHORT="${TRUNCATED}${FOOTER}"
fi

DELIMITER="__EOF_$(date +%s%N)__"

{
  echo "changelog_full<<${DELIMITER}"
  printf '%s\n' "$CHANGELOG_FULL"
  echo "${DELIMITER}"
  echo "changelog_short<<${DELIMITER}"
  printf '%s\n' "$CHANGELOG_SHORT"
  echo "${DELIMITER}"
  echo "previous_tag=${PREVIOUS_TAG}"
} >> "$GITHUB_OUTPUT"

echo "Previous tag: ${PREVIOUS_TAG:-<none>}"
echo "Full changelog: ${#CHANGELOG_FULL} chars"
echo "Short changelog: ${#CHANGELOG_SHORT} chars"
