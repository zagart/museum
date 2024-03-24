plugins {
    `android-library`
    `kotlin-android`
}

apply<PresentationModuleGradlePlugin>()

android {
    namespace = "com.zagart.museum.home.presentation"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":features:home:domain"))
    implementation(project(":shared:strings"))

    implementation(libs.paging.compose)
    implementation(libs.paging.runtime)
}