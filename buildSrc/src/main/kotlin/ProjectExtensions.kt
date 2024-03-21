import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

fun Project.android(): LibraryExtension {
    return extensions.getByType<LibraryExtension>()
}

fun Project.library(libraryAlias: String): String {
    val dependency = libs().findLibrary(libraryAlias).get().get()

    println("Library added: $dependency")

    return dependency.toString()
}

fun Project.libs(): VersionCatalog {
    return rootProject
        .extensions
        .getByType<VersionCatalogsExtension>()
        .named("libs")
}

fun Project.setupHilt() {
    pluginManager.apply("com.google.dagger.hilt.android")

    dependencies {
        ksp(library("hilt-android-compiler"))
        implementation(library("hilt-android"))
        implementation(library("hilt-navigation-compose"))
    }
}

fun Project.setupCompose() {
    dependencies {
        implementation(platform(library("compose-bom")))
        implementation(library("activity-compose"))
        implementation(library("compose-material3"))
        implementation(library("compose-ui"))
        implementation(library("compose-ui-graphics"))
        implementation(library("compose-ui-tooling"))
        implementation(library("compose-ui-tooling-preview"))
        implementation(library("lifecycle-runtime-compose"))
        implementation(library("lifecycle-viewmodel-compose"))
    }
}