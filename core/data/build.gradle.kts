plugins {
    `android-library`
    `kotlin-android`
}

apply<DataModuleGradlePlugin>()

android {
    namespace = "com.zagart.museum.core.data"
}

dependencies {
    implementation(project(":features:home:data"))
}