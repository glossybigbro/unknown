import io.guthub.unknown.convention.extension.setNamespace

plugins {
    id("convention.android.library")
    id("kotlinx-serialization")
}

android {
    setNamespace("core.data.api")
}

dependencies {
    implementation(projects.core.model)
}