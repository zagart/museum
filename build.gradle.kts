buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
}