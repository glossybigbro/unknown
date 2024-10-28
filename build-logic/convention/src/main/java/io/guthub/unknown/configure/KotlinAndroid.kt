package io.guthub.unknown.configure

import io.guthub.unknown.extension.androidExtension
import io.guthub.unknown.extension.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

@Suppress("UnstableApiUsage")
internal fun Project.configureKotlinAndroid() {
    pluginManager.apply("org.jetbrains.kotlin.android")

    androidExtension.apply {
        compileSdk = 34

        defaultConfig {
            minSdk = 24
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            // https://developer.android.com/studio/write/java8-support
            isCoreLibraryDesugaringEnabled = true
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }

            getByName("debug") {
                isMinifyEnabled = false
            }
        }

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }

    configureKotlin<KotlinAndroidProjectExtension>()

    dependencies {
        // https://developer.android.com/studio/write/java8-support
        add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
    }
}