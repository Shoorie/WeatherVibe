"""Parse per-module Kover XML reports and emit a markdown coverage summary.

Reads per-module XML reports matched by the REPORTS_GLOB env var plus the root
aggregated report pointed to by ROOT_XML, groups them by Gradle module (derived
from the path prefix) and writes two markdown files:

  * $GITHUB_STEP_SUMMARY   — appended so the table shows on the workflow run page
  * kover-summary.md       — standalone file for the follow-up comment step

When BASELINE_DIR is set, loads a previous coverage snapshot and renders per-module
deltas so reviewers can see how the PR affected coverage.

Each per-module row also carries a "Tests" column sourced from JUnit XML reports
under <module>/build/test-results/<variant>/*.xml so reviewers can tell how much
of the module is actually exercised.
"""

import glob
import os
import xml.etree.ElementTree as ET
from pathlib import Path

LINE_COUNTER = "LINE"
BRANCH_COUNTER = "BRANCH"
SUMMARY_FILE = "kover-summary.md"
TEST_RESULT_DIRS = ("testDebugUnitTest", "test", "testReleaseUnitTest")


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


def module_base_dir(path: str) -> Path:
    parts = Path(path).parts
    idx = parts.index("build")
    return Path(*parts[:idx])


def coverage_emoji(percent: float) -> str:
    if percent >= 60:
        return "🟢"
    if percent >= 40:
        return "🟡"
    return "🔴"


def module_from_baseline_filename(filename: str) -> str:
    stem = Path(filename).stem
    return ":" + stem.replace("__", ":")


def read_test_totals(base_dir: Path) -> dict[str, int]:
    totals = {"tests": 0, "skipped": 0, "failures": 0, "errors": 0}
    for variant in TEST_RESULT_DIRS:
        variant_dir = base_dir / "build" / "test-results" / variant
        xml_files = sorted(variant_dir.glob("*.xml")) if variant_dir.is_dir() else []
        if not xml_files:
            continue
        for xml_file in xml_files:
            suite = ET.parse(xml_file).getroot()
            totals["tests"] += int(suite.get("tests", "0"))
            totals["skipped"] += int(suite.get("skipped", "0"))
            totals["failures"] += int(suite.get("failures", "0"))
            totals["errors"] += int(suite.get("errors", "0"))
        break
    return totals


def format_test_cell(totals: dict[str, int]) -> str:
    tests = totals["tests"]
    if tests == 0:
        return "—"
    failed = totals["failures"] + totals["errors"]
    passed = tests - totals["skipped"] - failed
    segments = [f"{passed} ✓"]
    if failed > 0:
        segments.append(f"{failed} ✗")
    if totals["skipped"] > 0:
        segments.append(f"{totals['skipped']} skip")
    return " · ".join(segments)


def load_baseline(baseline_dir: str) -> dict[str, dict[str, float]]:
    result: dict[str, dict[str, float]] = {}
    baseline_path = Path(baseline_dir)
    if not baseline_path.is_dir():
        return result
    for xml_file in baseline_path.glob("*.xml"):
        if xml_file.name == "__root__.xml":
            lm, lc, bm, bc = read_counters(str(xml_file))
            result["__overall__"] = {
                "line_pct": percentage(lc, lm),
                "branch_pct": percentage(bc, bm),
            }
        else:
            module = module_from_baseline_filename(xml_file.name)
            lm, lc, bm, bc = read_counters(str(xml_file))
            if lm + lc > 0:
                result[module] = {
                    "line_pct": percentage(lc, lm),
                    "branch_pct": percentage(bc, bm),
                }
    return result


def format_delta(current: float, baseline: float | None) -> str:
    if baseline is None:
        return "—"
    delta = current - baseline
    if abs(delta) < 0.05:
        return "±0.0%"
    arrow = "↑" if delta > 0 else "↓"
    return f"{delta:+.1f}% {arrow}"


def build_module_rows(reports_glob: str, root_xml: str) -> list[dict]:
    root_path = Path(root_xml).resolve()
    rows: list[dict] = []
    for path in sorted(glob.glob(reports_glob, recursive=True)):
        if Path(path).resolve() == root_path:
            continue
        module = module_from_path(path)
        line_missed, line_covered, branch_missed, branch_covered = read_counters(path)
        if line_missed + line_covered == 0:
            continue
        test_totals = read_test_totals(module_base_dir(path))
        rows.append(
            {
                "module": module,
                "line_pct": percentage(line_covered, line_missed),
                "line_covered": line_covered,
                "line_total": line_covered + line_missed,
                "branch_pct": percentage(branch_covered, branch_missed),
                "test_totals": test_totals,
            }
        )
    rows.sort(key=lambda row: -row["line_pct"])
    return rows


def aggregate_test_totals(rows: list[dict]) -> dict[str, int]:
    totals = {"tests": 0, "skipped": 0, "failures": 0, "errors": 0}
    for row in rows:
        for key in totals:
            totals[key] += row["test_totals"][key]
    return totals


def format_test_summary(totals: dict[str, int]) -> str:
    tests = totals["tests"]
    if tests == 0:
        return "**Tests:** no JUnit reports found"
    failed = totals["failures"] + totals["errors"]
    passed = tests - totals["skipped"] - failed
    segments = [f"**{passed} passed**"]
    if failed > 0:
        segments.append(f"{failed} failed")
    if totals["skipped"] > 0:
        segments.append(f"{totals['skipped']} skipped")
    return f"**Tests:** {' · '.join(segments)} (of {tests} total)"


def render_markdown(
    rows: list[dict],
    root_xml: str,
    baseline: dict[str, dict[str, float]],
) -> str:
    line_missed, line_covered, branch_missed, branch_covered = read_counters(root_xml)
    overall_line = percentage(line_covered, line_missed)
    overall_branch = percentage(branch_covered, branch_missed)
    overall_total = line_covered + line_missed

    baseline_overall = baseline.get("__overall__")
    bl_line = baseline_overall["line_pct"] if baseline_overall else None
    overall_delta = format_delta(overall_line, bl_line)
    total_test_summary = aggregate_test_totals(rows)

    lines = [
        "## 📊 Code Coverage",
        "",
        (
            f"**{overall_line:.1f}%** of code is covered by tests "
            f"({overall_delta})"
        ),
        "",
        format_test_summary(total_test_summary),
        "",
        "| Module | Coverage | Tests | Change |",
        "|---|---:|:---:|---:|",
    ]
    for row in rows:
        bl = baseline.get(row["module"])
        line_delta = format_delta(row["line_pct"], bl["line_pct"] if bl else None)
        lines.append(
            f"| `{row['module']}` | "
            f"{coverage_emoji(row['line_pct'])} {row['line_pct']:.1f}% | "
            f"{format_test_cell(row['test_totals'])} | "
            f"{line_delta} |"
        )

    lines.append("")
    if baseline_overall:
        delta_val = overall_line - baseline_overall["line_pct"]
        if abs(delta_val) < 0.05:
            direction = "unchanged"
        elif delta_val > 0:
            direction = f"improved by {abs(delta_val):.1f}%"
        else:
            direction = f"decreased by {abs(delta_val):.1f}%"
        lines.append(f"**Verdict:** Overall coverage {direction} compared to base branch.")
    else:
        lines.append("**Verdict:** No baseline available — showing absolute coverage only.")

    bl_branch = baseline_overall["branch_pct"] if baseline_overall else None
    branch_delta = format_delta(overall_branch, bl_branch)
    lines.append("")
    lines.append("<details><summary>Details</summary>")
    lines.append("")
    lines.append(
        f"Branch coverage: {overall_branch:.1f}% ({branch_delta}) "
        f"· Tested lines: {line_covered} / {overall_total}"
    )
    lines.append("")
    lines.append("🟢 ≥60% · 🟡 ≥40% · 🔴 <40%")
    lines.append("</details>")

    return "\n".join(lines) + "\n"


def main() -> None:
    reports_glob = os.environ["REPORTS_GLOB"]
    root_xml = os.environ["ROOT_XML"]
    baseline_dir = os.environ.get("BASELINE_DIR", "")

    baseline = load_baseline(baseline_dir) if baseline_dir else {}
    rows = build_module_rows(reports_glob, root_xml)
    summary = render_markdown(rows, root_xml, baseline)

    step_summary_path = os.environ.get("GITHUB_STEP_SUMMARY")
    if step_summary_path:
        with open(step_summary_path, "a", encoding="utf-8") as handle:
            handle.write(summary)

    Path(SUMMARY_FILE).write_text(summary, encoding="utf-8")


if __name__ == "__main__":
    main()
