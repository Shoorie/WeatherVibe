#!/usr/bin/env bash
# Collect per-module Kover XML reports into a flat directory for caching.
#
# Each module report is renamed so the Gradle path is encoded in the filename:
#   feature/login/build/reports/kover/report.xml  →  feature__login.xml
#   build/reports/kover/report.xml (root)           →  __root__.xml
#
# The output directory (.kover-baseline/) is then cached by the workflow
# and later restored in PR builds for delta comparison.

set -euo pipefail

OUTPUT_DIR=".kover-baseline"

mkdir -p "$OUTPUT_DIR"

for xml in $(find . -path '*/build/reports/kover/report.xml' ! -path './build/*'); do
  module_path="${xml#./}"
  module_path="${module_path%/build/reports/kover/report.xml}"
  encoded="${module_path//\//__}"
  cp "$xml" "${OUTPUT_DIR}/${encoded}.xml"
done

if [ -f build/reports/kover/report.xml ]; then
  cp build/reports/kover/report.xml "${OUTPUT_DIR}/__root__.xml"
fi
