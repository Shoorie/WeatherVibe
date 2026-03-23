import com.google.devtools.ksp.gradle.KspExtension
import com.weather.vibe.Plugins.ksp
import com.weather.vibe.api
import com.weather.vibe.implementation
import com.weather.vibe.ksp
import com.weather.vibe.libs
import com.weather.vibe.roomCompiler
import com.weather.vibe.roomKtx
import com.weather.vibe.roomRuntime
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class RoomPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {

      apply(plugin = ksp)

      extensions.configure<KspExtension> {
        arg("room.schemaLocation", "$projectDir/schemas")
        arg("room.incremental", "true")
      }

      dependencies {
        api(libs.roomRuntime)
        implementation(libs.roomKtx)
        ksp(libs.roomCompiler)
      }
    }
  }
}
