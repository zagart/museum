plugins {
    `android-library`
    `kotlin-android`
}

apply<PresentationModuleGradlePlugin>()

android {
    namespace = "com.zagart.museum.settings.presentation"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":features:settings:domain"))
    implementation(project(":shared:strings"))
}