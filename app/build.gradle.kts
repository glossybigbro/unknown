plugins {
    id("convention.android.application")
    id("convention.android.compose")
}

android {
    namespace = "io.github.unknown"

    defaultConfig {
        applicationId = "io.github.unknown"
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("debug") {
            matchingFallbacks.add("release")
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = false
        }
    }
}

dependencies {
    implementation(projects.feature.main)
    implementation(projects.core.designsystem)
}