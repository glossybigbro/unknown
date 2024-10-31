import io.guthub.unknown.configure.configureCoroutineAndroid
import io.guthub.unknown.configure.configureKotlinAndroid

plugins {
    id("com.android.library")
}

configureKotlinAndroid()
configureCoroutineAndroid()