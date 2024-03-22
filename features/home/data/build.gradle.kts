plugins {
    `android-library`
    `kotlin-android`
}

apply<DataModuleGradlePlugin>()

android {
    namespace = "com.zagart.museum.home.data"
}

dependencies {
    implementation(project(":api"))
    implementation(project(":core:di"))
    implementation(project(":features:home:domain"))
}