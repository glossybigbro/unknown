import io.guthub.unknown.convention.extension.setNamespace

plugins {
    id("convention.android.library")
}

android {
    setNamespace("core.domain")
}

dependencies {
    implementation(libs.inject)
}