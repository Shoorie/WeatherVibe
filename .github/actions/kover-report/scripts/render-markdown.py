"""Parse per-module Kover XML reports and emit a markdown coverage summary.

Reads per-module XML reports matched by the REPORTS_GLOB env var plus the root
aggregated report pointed to by ROOT_XML, groups them by Gradle module (derived
from the path prefix) and writes two markdown files:

  * $GITHUB_STEP_SUMMARY   — appended so the table shows on the workflow run page
  * kover-summary.md       — standalone file for the follow-up comment step

Test fixture modules (:testing:*) are filtered out because they carry no
production code.
"""

import glob
import os
import xml.etree.ElementTree as ET
from pathlib import Path

LINE_COUNTER = "LINE"
BRANCH_COUNTER = "BRANCH"
EXCLUDED_MODULE_PREFIXES = (":testing:",)
SUMMARY_FILE = "kover-summary.md"


def read_counters(path: str) -> tuple[int, int, int, int]:
    tree = ET.parse(path)
    root = tree.getroot()
    line_missed = line_covered = 0
    branch_missed = branch_covered = 0
    for counter in root.findall("./counter"):
        ctype = counter.get("type")
        missed = int(counter.get("missed", "0"))
        covered = int(counter.get("covered", "0"))
        if ctype == LINE_COUNTER:
            line_missed += missed
            line_covered += covered
        elif ctype == BRANCH_COUNTER:
            branch_missed += missed
            branch_covered += covered
    return line_missed, line_covered, branch_missed, branch_covered


def percentage(covered: int, missed: int) -> float:
    total = covered + missed
    return (covered / total * 100) if total > 0 else 0.0


def module_from_path(path: str) -> str:
    parts = Path(path).parts
    idx = parts.index("build")
    return ":" + ":".join(parts[:idx]) if idx > 0 else ":"


def coverage_emoji(percent: float) -> str:
    if percent >= 80:
        return "🟢"
    if percent >= 50:
        return "🟡"
    return "🔴"


def build_module_rows(reports_glob: str, root_xml: str) -> list[dict]:
    root_path = Path(root_xml).resolve()
    rows: list[dict] = []
    for path in sorted(glob.glob(reports_glob, recursive=True)):
        if Path(path).resolve() == root_path:
            continue
        module = module_from_path(path)
        if module.startswith(EXCLUDED_MODULE_PREFIXES):
            continue
        line_missed, line_covered, branch_missed, branch_covered = read_counters(path)
        if line_missed + line_covered == 0:
            continue
        rows.append(
            {
                "module": module,
                "line_pct": percentage(line_covered, line_missed),
                "line_covered": line_covered,
                "line_total": line_covered + line_missed,
                "branch_pct": percentage(branch_covered, branch_missed),
            }
        )
    rows.sort(key=lambda row: -row["line_pct"])
    return rows


def render_markdown(rows: list[dict], root_xml: str) -> str:
    line_missed, line_covered, branch_missed, branch_covered = read_counters(root_xml)
    overall_line = percentage(line_covered, line_missed)
    overall_branch = percentage(branch_covered, branch_missed)
    overall_total = line_covered + line_missed

    lines = [
        "## 📊 Code Coverage",
        "",
        (
            f"**Overall:** {coverage_emoji(overall_line)} "
            f"{overall_line:.1f}% line · "
            f"{overall_branch:.1f}% branch · "
            f"{line_covered} / {overall_total} lines"
        ),
        "",
        "| Module | Line | Branch | Covered / Total |",
        "|---|---:|---:|---:|",
    ]
    for row in rows:
        lines.append(
            f"| `{row['module']}` | "
            f"{coverage_emoji(row['line_pct'])} {row['line_pct']:.1f}% | "
            f"{row['branch_pct']:.1f}% | "
            f"{row['line_covered']} / {row['line_total']} |"
        )
    return "\n".join(lines) + "\n"


def main() -> None:
    reports_glob = os.environ["REPORTS_GLOB"]
    root_xml = os.environ["ROOT_XML"]

    rows = build_module_rows(reports_glob, root_xml)
    summary = render_markdown(rows, root_xml)

    step_summary_path = os.environ.get("GITHUB_STEP_SUMMARY")
    if step_summary_path:
        with open(step_summary_path, "a", encoding="utf-8") as handle:
            handle.write(summary)

    Path(SUMMARY_FILE).write_text(summary, encoding="utf-8")


if __name__ == "__main__":
    main()
