import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DataModuleGradlePlugin : LibraryGradlePlugin() {

    override fun apply(project: Project) {
        super.apply(project)

        with (project) {
            dependencies {
                setupRoom()
            }
        }
    }
}