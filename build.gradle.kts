// Top-level build file where you can add configuration options common to all sub-projects/modules.
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.compose) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.protobuf) apply false
  alias(libs.plugins.google.services) apply false
  alias(libs.plugins.firebase.appdistribution) apply false
  alias(libs.plugins.detekt) apply false
  alias(libs.plugins.kover)
}

val detektReportMerge by tasks.registering(ReportMergeTask::class) {
  output.set(rootProject.layout.buildDirectory.file("reports/detekt/merged.sarif"))
}

subprojects {
  afterEvaluate {
    if (plugins.hasPlugin("org.jetbrains.kotlinx.kover")) {
      rootProject.dependencies.add("kover", this)
    }

    tasks.withType<Detekt>().configureEach {
      finalizedBy(detektReportMerge)
    }

    detektReportMerge.configure {
      input.from(tasks.withType<Detekt>().map { it.sarifReportFile })
    }
  }
}
