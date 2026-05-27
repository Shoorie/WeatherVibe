# CI release scripts

Helper scripts called from the release workflows in `.github/workflows/`
via the composite actions in `.github/actions/`. Each script reads its
inputs from environment variables, writes outputs to `$GITHUB_OUTPUT`,
and exits non-zero on any failure.

| Script | Purpose |
|--------|---------|
| `read-version.sh` | Read the application version from `version.txt` and derive the matching integer version code. |
| `generate-changelog.sh` | Build a Markdown changelog of commits since the previous release tag, plus a length-capped variant for Firebase / Slack. |
| `tag-release.sh` | Create the `vX.Y.Z` annotated tag for the version that was just released, idempotently. |
| `bump-version.sh` | Advance the patch segment of `version.txt` and push the bump commit back to the branch. |

Run each script directly when debugging — they are pure bash and need
only a checked-out repository to function.

## CI review notification scripts

Helper scripts called from `.github/workflows/notify-review.yml`. Read
inputs from environment variables, write outputs to `$GITHUB_OUTPUT`.
Each fails open — the workflow step is marked `continue-on-error` so
a missing or malformed input never blocks a PR.

| Script | Purpose |
|--------|---------|
| `notify-review/resolve-mention.sh` | Look up Slack member ID from `.github/reviewers.yml` for the PR author; falls back to plain `@login`. |
| `notify-review/resolve-ticket.sh` | Extract the first ticket reference (`[A-Z]+-[0-9]+`) from the PR title; falls back to `${FALLBACK_TICKET_PREFIX}{pr-number}`. |
| `notify-review/escape-title.sh` | JSON-escape the PR title (`\` and `"`) so it can be safely interpolated into the Slack payload. |
