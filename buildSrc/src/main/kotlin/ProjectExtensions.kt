import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

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

fun Project.setupRoom() {
    dependencies {
        ksp(library("room-compiler"))
        implementation(library("room"))
        implementation(library("room-ktx"))
        implementation(library("room-paging"))
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

fun Project.setupTests() {
    setupRoom()
    dependencies {
        implementation(library("compose-ui-test-junit4"))
        implementation(library("compose-ui-test-manifest"))
        implementation(library("test-runner"))
        androidTestImplementation(library("hilt-android-testing"))
    }
}

fun Project.addHomeFeature() {
    dependencies {
        implementation(project(":api"))
        implementation(project(":features:home:data"))
        implementation(project(":features:home:domain"))
        implementation(project(":features:home:presentation"))

        implementation(library("paging-compose"))
    }
}

fun Project.addDetailsFeature() {
    dependencies {
        implementation(project(":api"))
        implementation(project(":features:details:data"))
        implementation(project(":features:details:domain"))
        implementation(project(":features:details:presentation"))
    }
}

fun Project.addSettingsFeature() {
    dependencies {
        implementation(project(":features:settings:data"))
        implementation(project(":features:settings:domain"))
        implementation(project(":features:settings:presentation"))

        implementation(library("datastore-preferences"))
    }
}