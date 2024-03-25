plugins {
    `android-library`
    `kotlin-android`
}

apply<LibraryGradlePlugin>()

android {
    namespace = "com.zagart.museum.core.ui"
}

dependencies {
    implementation(project(":shared:strings"))
    setupCompose()
    implementation(libs.material.icons.extended)
    implementation(libs.coil)
}