plugins {
    `android-library`
    `kotlin-android`
}

apply<PresentationModuleGradlePlugin>()

android {
    namespace = "com.zagart.museum.home.presentation"
}