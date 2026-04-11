// Top-level build file where you can add configuration options common to all sub-projects/modules.
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

dependencies {
  kover(projects.app)
  kover(projects.core.ai)
  kover(projects.core.designsystem)
  kover(projects.core.network)
  kover(projects.core.time)
  kover(projects.domain.location)
  kover(projects.domain.settings)
  kover(projects.domain.weather)
  kover(projects.data.location)
  kover(projects.data.settings)
  kover(projects.data.weather)
  kover(projects.feature.home)
  kover(projects.feature.search)
  kover(projects.feature.settings)
  kover(projects.feature.splash)
}
