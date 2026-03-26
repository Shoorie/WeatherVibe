plugins {
  alias(libs.plugins.weathervibe.android.library)
  alias(libs.plugins.weathervibe.android.koin)
  alias(libs.plugins.weathervibe.android.room)
  alias(libs.plugins.weathervibe.android.ktor)
  alias(libs.plugins.protobuf)
}

android {
  namespace = "com.weather.vibe.data.weather"
}

protobuf {
  protoc {
    artifact = libs.protobuf.protoc.get().toString()
  }
  generateProtoTasks {
    all().forEach { task ->
      task.builtins {
        create("java") { option("lite") }
        create("kotlin") { option("lite") }
      }
    }
  }
}

dependencies {
  implementation(project(":core:ai"))
  implementation(project(":core:network"))
  implementation(project(":domain:weather"))

  implementation(libs.koin.android)
  implementation(libs.androidx.datastore)
  implementation(libs.protobuf.kotlin.lite)
}
