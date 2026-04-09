import com.weather.vibe.coroutinesTest
import com.weather.vibe.junit
import com.weather.vibe.libs
import com.weather.vibe.mockk
import com.weather.vibe.striktCore
import com.weather.vibe.testImplementation
import com.weather.vibe.turbine
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class TestPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {

      dependencies {
        testImplementation(libs.junit)
        testImplementation(libs.coroutinesTest)
        testImplementation(libs.mockk)
        testImplementation(libs.striktCore)
        testImplementation(libs.turbine)
      }
    }
  }
}
