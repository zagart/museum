plugins {
    `android-library`
    `kotlin-android`
}

apply<PresentationModuleGradlePlugin>()

android {
    namespace = "com.zagart.museum.details.presentation"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":features:home:domain"))
    implementation(project(":features:home:presentation"))
    implementation(project(":features:details:domain"))
    implementation(project(":shared:strings"))
}