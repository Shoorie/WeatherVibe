import com.weather.vibe.Plugins.kotlinSerialization
import com.weather.vibe.implementation
import com.weather.vibe.kotlinxSerializationJson
import com.weather.vibe.ktorClientOkhttp
import com.weather.vibe.ktorContentNegotiation
import com.weather.vibe.ktorSerializationJson
import com.weather.vibe.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class KtorPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {

      apply(plugin = kotlinSerialization)

      dependencies {
        implementation(libs.ktorClientOkhttp)
        implementation(libs.ktorContentNegotiation)
        implementation(libs.ktorSerializationJson)
        implementation(libs.kotlinxSerializationJson)
      }
    }
  }
}
