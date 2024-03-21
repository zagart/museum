import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class PresentationModuleGradlePlugin : LibraryGradlePlugin() {

    override fun apply(project: Project) {
        super.apply(project)

        with(project) {
            setupCompose()
            dependencies {
                implementation(library("coil"))
                implementation(library("core-ktx"))
                implementation(library("lifecycle-runtime-ktx"))
            }
        }
    }
}