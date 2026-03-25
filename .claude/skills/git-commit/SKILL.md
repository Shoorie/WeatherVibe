---
name: commit
description: >-
  Creates clean single-line git commits. Use when user says: commit, push, save changes, "git commit",
  or any variation of committing to version control.
allowed-tools:
  - Bash(git status:*)
  - Bash(git diff:*)
  - Bash(git log:*)
  - Bash(git add:*)
  - Bash(git commit:*)
  - Bash(git push:*)
  - Bash(git branch:*)
disable-model-invocation: true
---

# Automated Clean Commits

1. **Inspect:** Run `git diff` and `git diff --staged`.
2. **Analyze:** Understand the core change.
3. **Draft message:** Single line, capitalized, under 72 chars. No body, no trailers, no
   `Co-Authored-By`.
4. **Stage:** `git add -A`
5. **Commit:** `git commit -m "Your message"`
6. **Push:** Push to current remote branch. If no upstream: `git push -u origin <branch>`.
