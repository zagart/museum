plugins {
    `android-library`
    `kotlin-android`
}

apply<DataModuleGradlePlugin>()

android {
    namespace = "com.zagart.museum.details.data"
}

dependencies {
    implementation(project(":features:details:domain"))
    implementation(project(":features:home:domain"))
}