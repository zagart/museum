plugins {
    `android-library`
    `kotlin-android`
}

apply<DataModuleGradlePlugin>()

android {
    namespace = "com.zagart.museum.settings.data"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":shared:strings"))
    implementation(project(":features:settings:domain"))

    implementation(libs.datastore.preferences)
}