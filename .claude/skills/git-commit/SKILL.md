---
name: commit
description: >-
  Analyzes repository changes and creates clean, single-line git commits. Use this
  skill when the user asks to commit, push, save changes, or says "commit this",
  "push my changes", "git commit", or any variation of committing code to version
  control.
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

Follow these exact steps strictly in order:

1. **Inspect Changes:** Run `git diff` (and `git diff --staged` if needed) to see all currently
   modified, added, or deleted files.
2. **Analyze the Diff:** Understand the core logic of what changed.
3. **Draft the Commit Message:** Write ONE single-line commit message.
   - Start directly with the action description (e.g., "Update home screen layout").
   - The very first letter MUST be capitalized.
   - Strictly under 72 characters.
   - **FORBIDDEN:** Do NOT add body text, bullet points, details, or explanations after the
     subject line. The commit message is ALWAYS a single line.
   - **FORBIDDEN:** Do NOT add any trailers like `Co-Authored-By`, `Signed-off-by`, or similar.
     The commit must look like it was made entirely by the user.
4. **Stage Changes:** Run `git add -A` to stage all current changes.
5. **Execute Commit:** Run `git commit -m "Your single line message"`.
   Use the `-m` flag with a single string. Do NOT use multi-line `-m` flags or `--trailer`.
6. **Push to Remote:** Push the changes to the current remote branch. If the branch has no
   upstream branch configured, set it using `git push -u origin <branch>`.
