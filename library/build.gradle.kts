import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(voniLibs.plugins.kotlinMultiplatform)
    id("com.android.kotlin.multiplatform.library")
    alias(voniLibs.plugins.composeMultiplatform)
    alias(voniLibs.plugins.kotlin.compose)
    alias(voniLibs.plugins.vanniktechMavenPublish)
}

group = "tz.co.pesalock"
version = "1.0.1"

kotlin {
    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    androidLibrary {
        namespace = "tz.co.pesalock.voni"
        compileSdk = voniLibs.versions.android.compileSdk.get().toInt()
        minSdk = voniLibs.versions.android.minSdk.get().toInt()
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
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

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "voni", version.toString())

    pom {
        name = "Voni"
        description = "A multiplatform UI library for composable, orientation setup and charts."
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
                email = "software@pesalock.co.tz"
                organization = "Pesalock Africa Limited"
                organizationUrl = "https://pesalock.co.tz"
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
