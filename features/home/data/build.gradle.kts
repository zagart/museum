plugins {
    `android-library`
    `kotlin-android`
}

apply<DataModuleGradlePlugin>()

android {
    namespace = "com.zagart.museum.home.data"
}

dependencies {
    implementation(project(":features:home:domain"))
}