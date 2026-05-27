#!/usr/bin/env bash
set -euo pipefail

readonly MAX_LENGTH=200
readonly ELLIPSIS="…"

first_paragraph=$(printf '%s' "$PR_BODY" \
  | tr -d '\r' \
  | awk 'BEGIN{RS=""} {print; exit}' \
  | tr '\n' ' ' \
  | sed -E 's/  +/ /g; s/^ //; s/ $//')

if [ -z "$first_paragraph" ]; then
  first_paragraph="$PR_TITLE"
fi

first_paragraph=$(printf '%s' "$first_paragraph" | awk -v max="$MAX_LENGTH" -v ell="$ELLIPSIS" '{
  if (length($0) > max) print substr($0, 1, max) ell
  else print $0
}')

json_safe=$(printf '%s' "$first_paragraph" | sed -e 's/\\/\\\\/g' -e 's/"/\\"/g')

{
  echo "value<<SUMMARY_EOF"
  echo "$json_safe"
  echo "SUMMARY_EOF"
} >> "$GITHUB_OUTPUT"
