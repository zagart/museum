plugins {
    `android-library`
    `kotlin-android`
}

apply<LibraryGradlePlugin>()

android {
    namespace = "com.zagart.museum.core.data"
}

dependencies {
    setupRoom()
    implementation(project(":features:home:data"))
    implementation(project(":features:details:data"))
    implementation(libs.datastore.preferences)
}