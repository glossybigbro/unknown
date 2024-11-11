import io.guthub.unknown.convention.configure.configureCoroutineAndroid
import io.guthub.unknown.convention.configure.configureKotlinAndroid

plugins {
    id("com.android.library")
}

configureKotlinAndroid()
configureCoroutineAndroid()