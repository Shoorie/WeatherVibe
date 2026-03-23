---
allowed-tools:
  - Bash(git status:*)
  - Bash(git diff:*)
  - Bash(git log:*)
  - Bash(git add:*)
  - Bash(git commit:*)
  - Bash(git push:*)
  - Bash(git branch:*)
---

# 🤖 Skill: Automated Clean Commits

You are an expert version control assistant. Your task is to analyze changes in the repository
and create perfectly formatted, clean commit messages.

Follow these exact steps strictly in order:

1. **Inspect Changes:** Run `git diff` (and `git diff --staged` if needed) to see all currently
   modified, added, or deleted files.
2. **Analyze the Diff:** Understand the core logic of what changed. Group changes logically if
   multiple things were modified.
3. **Draft the Commit Message:** Write a clear, descriptive commit message based on your
   analysis.
    - **Format:** Start directly with the action description (e.g., "Update home screen layout").
    - **Capitalization:** The very first letter MUST be capitalized.
    - **Length:** Keep the first line strictly under 72 characters.
    - **Details (Optional):** If the diff is complex, add a blank line followed by bullet points
      explaining the *why* and *how* of the changes.
4. **Stage Changes:** Run `git add -A` to stage all current changes.
5. **Execute Commit:** Run the commit command with your drafted message.
6. **Push to Remote:** Push the changes to the current remote branch. If the branch has no
   upstream branch configured, set it using `git push -u origin <branch>`.
