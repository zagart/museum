import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class DataModuleGradlePlugin : LibraryGradlePlugin() {

    override fun apply(project: Project) {
        super.apply(project)

        with(project) {
            dependencies {
                setupRoom()
                implementation(project(":api"))
                implementation(project(":core:di"))
            }
        }
    }
}