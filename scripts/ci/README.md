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
