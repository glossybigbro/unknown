import io.unknown.configure.configureFlavors
import io.unknown.configure.configureHiltAndroid
import io.unknown.configure.configureKotlinAndroid

plugins {
    id("com.android.application")
}

configureFlavors()
configureKotlinAndroid()
configureHiltAndroid()