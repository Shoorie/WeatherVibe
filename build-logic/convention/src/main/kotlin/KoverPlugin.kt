import com.weather.vibe.Plugins.kover
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class KoverPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    with(target) {

      apply(plugin = kover)

      configureReports()
    }
  }

  private fun Project.configureReports() {
    extensions.configure<KoverProjectExtension> {
      reports {
        filters {
          excludes {
            classes(*EXCLUDED_CLASSES.toTypedArray())
            packages(*EXCLUDED_PACKAGES.toTypedArray())
            annotatedBy(*EXCLUDED_ANNOTATIONS.toTypedArray())
          }
        }
        total {
          html {
            onCheck.set(false)
          }
          xml {
            onCheck.set(false)
          }
        }
      }
    }
  }

  private companion object {

    val EXCLUDED_CLASSES = listOf(
      "*.BuildConfig",
      "*.ComposableSingletons*",
      "*.*Preview*",
      "*.*PreviewKt*",
      "*.*_Impl*",
      "*.*_Factory",
      "*.*_Factory$*",
      "*.*Module$*",
      "*.R",
      "*.R$*",
      "*.Manifest*",
    )

    val EXCLUDED_PACKAGES = listOf(
      "org.koin.ksp.generated",
      "hilt_aggregated_deps",
      "dagger.hilt.internal.aggregatedroot.codegen",
    )

    val EXCLUDED_ANNOTATIONS = listOf(
      "androidx.compose.ui.tooling.preview.Preview",
      "androidx.compose.ui.tooling.preview.PreviewParameter",
    )
  }
}
