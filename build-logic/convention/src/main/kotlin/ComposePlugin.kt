import com.weather.vibe.Plugins.kotlinCompose
import com.weather.vibe.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class ComposePlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {
      apply(plugin = kotlinCompose)
      configureAndroidCompose()
    }
  }
}
