plugins {
    `android-library`
    `kotlin-android`
}

apply<LibraryGradlePlugin>()

android {
    namespace = "com.zagart.museum.core.di"
}

dependencies {
    implementation(project(":shared:strings"))
}