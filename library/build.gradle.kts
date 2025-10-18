import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(voniLibs.plugins.kotlinMultiplatform)
    alias(voniLibs.plugins.androidLibrary)
    alias(voniLibs.plugins.composeMultiplatform)
    alias(voniLibs.plugins.kotlin.compose)
    alias(voniLibs.plugins.vanniktechMavenPublish)
}

group = "io.github.pesalock"
// This is a pre-release, internal-only version.
version = "1.0.0-alpha01"

kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release")
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(voniLibs.kotlinx.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(voniLibs.kotlin.test)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.common)
            }
        }
    }
}

android {
    namespace = "io.github.pesalock.voni"
    compileSdk = voniLibs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = voniLibs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01)

    if (project.hasProperty("signing.keyId")) {
        signAllPublications()
    }

    coordinates(group.toString(), "voni", version.toString())

    pom {
        name = "Voni"
        description = "A multiplatform UI library for composables, orientation setup and charts."
        inceptionYear = "2025"
        url = "https://github.com/Pesalock/Voni"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "pesalock"
                name = "Pesalock Africa Limited"
                url = "https://pesalock.co.tz"
            }
        }
        scm {
            url = "https://github.com/Pesalock/Voni"
            connection = "scm:git:github.com/Pesalock/Voni.git"
            developerConnection = "scm:git:ssh://github.com/Pesalock/Voni.git"
        }
    }
}
