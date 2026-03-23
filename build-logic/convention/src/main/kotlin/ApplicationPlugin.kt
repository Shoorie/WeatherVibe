import com.android.build.api.dsl.ApplicationExtension
import com.weather.vibe.Plugins.androidApplication
import com.weather.vibe.Plugins.kotlinAndroid
import com.weather.vibe.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class ApplicationPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {

      apply(plugin = androidApplication)
      apply(plugin = kotlinAndroid)

      extensions.configure<ApplicationExtension> {
        configureKotlinAndroid(this)
        defaultConfig.targetSdk = 36
      }
    }
  }
}
