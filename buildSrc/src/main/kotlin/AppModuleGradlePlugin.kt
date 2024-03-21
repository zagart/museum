import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AppModuleGradlePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            dependencies {
                //Main dependencies
                implementation(library("activity.compose"))
                implementation(library("core.ktx"))
                implementation(library("compose.material3"))
                implementation(library("compose.ui"))
                implementation(library("compose.ui.tooling"))
                implementation(platform(library("compose.bom")))

                //Modules
                implementation(project(":shared:strings"))
            }
        }
    }
}