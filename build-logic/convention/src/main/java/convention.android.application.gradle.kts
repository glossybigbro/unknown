import io.guthub.unknown.configure.configureFlavors
import io.guthub.unknown.configure.configureHiltAndroid
import io.guthub.unknown.configure.configureKotlinAndroid

plugins {
    id("com.android.application")
}

configureFlavors()
configureKotlinAndroid()
configureHiltAndroid()