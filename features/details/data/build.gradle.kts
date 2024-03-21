plugins {
    `android-library`
    `kotlin-android`
}

apply<DataModuleGradlePlugin>()

android {
    namespace = "com.zagart.museum.details.data"
}