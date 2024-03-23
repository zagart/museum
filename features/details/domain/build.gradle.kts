plugins {
    `android-library`
    `kotlin-android`
}

apply<DomainModuleGradlePlugin>()

android {
    namespace = "com.zagart.museum.details.domain"
}

dependencies {
    implementation(project(":features:home:domain"))
}