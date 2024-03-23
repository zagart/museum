plugins {
    `android-library`
    `kotlin-android`
}

apply<PresentationModuleGradlePlugin>()

android {
    namespace = "com.zagart.museum.details.presentation"
}

dependencies {
    implementation(project(":features:home:domain"))
    implementation(project(":features:details:domain"))
}