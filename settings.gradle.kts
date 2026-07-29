pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.kotlin.multiplatform.library") version "9.0.0"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("voniLibs") {
            from(files("gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "voni-build"
include(":voni")
project(":voni").projectDir = file("library")
