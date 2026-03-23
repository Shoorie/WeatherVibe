import com.google.devtools.ksp.gradle.KspExtension
import com.weather.vibe.Plugins.ksp
import com.weather.vibe.implementation
import com.weather.vibe.koinAnnotations
import com.weather.vibe.koinKspCompiler
import com.weather.vibe.ksp
import com.weather.vibe.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class KoinPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {

      apply(plugin = ksp)

      extensions.configure<KspExtension> {
        arg("KOIN_CONFIG_CHECK", "false")
        arg("KOIN_DEFAULT_MODULE", "false")
      }

      dependencies {
        implementation(libs.koinAnnotations)
        ksp(libs.koinKspCompiler)
      }
    }
  }
}
