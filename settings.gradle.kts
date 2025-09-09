pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
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
