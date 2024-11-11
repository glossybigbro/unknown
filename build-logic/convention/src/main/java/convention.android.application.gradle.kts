import io.guthub.unknown.convention.configure.configureFlavors
import io.guthub.unknown.convention.configure.configureHiltAndroid
import io.guthub.unknown.convention.configure.configureKotlinAndroid

plugins {
    id("com.android.application")
}

configureFlavors()
configureKotlinAndroid()
configureHiltAndroid()