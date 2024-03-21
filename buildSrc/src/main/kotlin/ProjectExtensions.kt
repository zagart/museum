import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
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