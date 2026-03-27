import com.google.protobuf.gradle.ProtobufExtension
import com.weather.vibe.Plugins.protobuf
import com.weather.vibe.datastoreCore
import com.weather.vibe.implementation
import com.weather.vibe.libs
import com.weather.vibe.protobufKotlinLite
import com.weather.vibe.protobufProtoc
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class DatastorePlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {

      apply(plugin = protobuf)

      extensions.configure<ProtobufExtension> {
        protoc {
          artifact = libs.protobufProtoc.get().toString()
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
        implementation(libs.datastoreCore)
        implementation(libs.protobufKotlinLite)
      }
    }
  }
}
