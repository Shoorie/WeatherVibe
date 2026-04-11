import com.weather.vibe.Plugins.detekt
import com.weather.vibe.detektFormatting
import com.weather.vibe.detektPlugins
import com.weather.vibe.libs
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class DetektPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {

      apply(plugin = detekt)

      configureExtension()
      configureTasks()
      registerDependencies()
    }
  }

  private fun Project.configureExtension() {
    extensions.configure<DetektExtension> {
      buildUponDefaultConfig = true
      allRules = false
      parallel = true
      autoCorrect = false
      config.setFrom(rootProject.files(CONFIG_PATH))
      baseline = file(BASELINE_FILE)
      source.setFrom(files(SOURCE_DIRS))
    }
  }

  private fun Project.configureTasks() {
    tasks.withType<Detekt>().configureEach {
      jvmTarget = JVM_TARGET
      exclude(EXCLUDED_PATHS)
      reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(false)
        sarif.required.set(false)
        md.required.set(false)
      }
    }
  }

  private fun Project.registerDependencies() {
    dependencies {
      detektPlugins(libs.detektFormatting)
    }
  }

  private companion object {
    const val JVM_TARGET = "17"
    const val CONFIG_PATH = "config/detekt/detekt.yml"
    const val BASELINE_FILE = "detekt-baseline.xml"

    val SOURCE_DIRS = listOf(
      "src/main/kotlin",
      "src/main/java",
      "src/test/kotlin",
      "src/test/java",
    )

    val EXCLUDED_PATHS = listOf(
      "**/build/**",
      "**/generated/**",
      "**/resources/**",
    )
  }
}
