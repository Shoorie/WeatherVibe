import com.android.build.api.dsl.LibraryExtension
import com.weather.vibe.Plugins.androidLibrary
import com.weather.vibe.Plugins.kotlinAndroid
import com.weather.vibe.Plugins.weatherVibeDetekt
import com.weather.vibe.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class LibraryPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {

      apply(plugin = androidLibrary)
      apply(plugin = kotlinAndroid)
      apply(plugin = weatherVibeDetekt)

      extensions.configure<LibraryExtension> {
        configureKotlinAndroid(this)
      }
    }
  }
}
