plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.hilt)
}

apply<AppModuleGradlePlugin>()

android {
    namespace = "com.zagart.museum"
    compileSdk = ProjectConfig.compileSdkVersion

    defaultConfig {
        applicationId = "com.zagart.museum"
        minSdk = ProjectConfig.minSdkVersion
        targetSdk = ProjectConfig.targetSdkVersion
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "com.zagart.museum.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = ProjectConfig.kotlinCompilerExtensionVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    hilt {
        enableAggregatingTask = false
    }

    dependencies {
        addHomeFeature()
        addDetailsFeature()
        addSettingsFeature()

        implementation(project(":core:data"))
        implementation(project(":core:di"))
        implementation(project(":core:ui"))
        implementation(project(":shared:strings"))
        implementation(libs.appcompat)

        setupTests()
    }
}
