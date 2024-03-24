pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Museum"

include(":api")
include(":app")

include(":core:data")
include(":core:di")
include(":core:ui")

include(":features:details:data")
include(":features:details:presentation")
include(":features:details:domain")

include(":features:home:data")
include(":features:home:domain")
include(":features:home:presentation")

include(":features:settings:data")
include(":features:settings:domain")
include(":features:settings:presentation")

include(":shared:strings")
