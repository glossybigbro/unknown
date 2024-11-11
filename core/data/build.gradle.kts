import io.guthub.unknown.convention.extension.setNamespace

plugins {
    id("convention.android.library")
    id("convention.android.hilt")
    id("kotlinx-serialization")
}

android {
    setNamespace("core.data")
}

dependencies {

}