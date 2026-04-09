import com.weather.vibe.Plugins.weatherVibeCompose
import com.weather.vibe.Plugins.weatherVibeKoin
import com.weather.vibe.Plugins.weatherVibeLibrary
import com.weather.vibe.coroutinesAndroid
import com.weather.vibe.implementation
import com.weather.vibe.koinAndroid
import com.weather.vibe.koinAndroidxCompose
import com.weather.vibe.libs
import com.weather.vibe.lifecycleRuntimeCompose
import com.weather.vibe.lifecycleRuntimeKtx
import com.weather.vibe.lifecycleViewmodelCompose
import com.weather.vibe.materialIconsCore
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class FeaturePlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {

      apply(plugin = weatherVibeLibrary)
      apply(plugin = weatherVibeCompose)
      apply(plugin = weatherVibeKoin)

      dependencies {
        implementation(project(":core:designsystem"))
        implementation(libs.materialIconsCore)
        implementation(libs.lifecycleRuntimeKtx)
        implementation(libs.lifecycleViewmodelCompose)
        implementation(libs.lifecycleRuntimeCompose)
        implementation(libs.coroutinesAndroid)
        implementation(libs.koinAndroid)
        implementation(libs.koinAndroidxCompose)
      }
    }
  }
}
