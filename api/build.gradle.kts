plugins {
    `android-library`
    `kotlin-android`
    alias(libs.plugins.kotlin.serialization)
}

apply<LibraryGradlePlugin>()

android {
    namespace = "com.zagart.museum.api"
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.serialization.json)
}