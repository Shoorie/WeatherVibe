---
name: ops:pr
description: >-
  Creates a GitHub Pull Request from the current branch. Reads git log and diff vs main,
  generates a clear PR title and description, pushes the branch if needed, and opens the PR
  via gh CLI. Use when user says: create PR, open PR, push PR, submit for review,
  or any variation of creating a pull request.
allowed-tools:
  - Bash(git log:*)
  - Bash(git diff:*)
  - Bash(git status:*)
  - Bash(git push:*)
  - Bash(git branch:*)
  - Bash(gh pr create:*)
  - Bash(gh pr view:*)
---

# PR Creator

## Step 1 — Gather context

Run these in parallel:
- `git log main..HEAD --oneline` — list of commits on this branch
- `git diff main...HEAD --stat` — files changed
- `git diff main...HEAD` — full diff for understanding the changes
- `git branch --show-current` — current branch name

## Step 2 — Push if needed

Check if the branch has a remote upstream. If not, run:
```
git push -u origin <branch>
```

## Step 3 — Draft title and description

**Title:** Single line, max 72 chars, capitalized, imperative mood. Summarize the whole change,
not just one commit.

**Description template:**
```
## What
- Bullet points describing what was changed/added

## Why
- Motivation, context, or ticket reference if known
```

Keep it concise. Infer the "Why" from the code changes and commit messages.
The test plan should reflect what was actually changed — UI changes get manual UI steps,
use cases get logic verification steps, etc.

## Step 4 — Create the PR

```bash
gh pr create --title "<title>" --body "<description>" --base main
```

## Step 5 — Return the PR URL

Print the PR URL so the user can open it directly.
