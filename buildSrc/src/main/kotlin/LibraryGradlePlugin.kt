import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

open class LibraryGradlePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply("android-library")
            pluginManager.apply("kotlin-android")
            pluginManager.apply("com.google.devtools.ksp")

            setupHilt()

            with(android()) {
                compileSdk = ProjectConfig.compileSdkVersion

                defaultConfig.apply {
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
                buildFeatures {
                    compose = true
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
                composeOptions {
                    kotlinCompilerExtensionVersion = ProjectConfig.kotlinCompilerExtensionVersion
                }
                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }
        }
    }
}