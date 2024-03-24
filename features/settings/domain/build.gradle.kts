plugins {
    `android-library`
    `kotlin-android`
}

apply<DomainModuleGradlePlugin>()

android {
    namespace = "com.zagart.museum.settings.domain"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":shared:strings"))
}