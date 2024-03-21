plugins {
    `android-library`
    `kotlin-android`
}

apply<DomainModuleGradlePlugin>()

android {
    namespace = "com.zagart.museum.details.presentation"
}