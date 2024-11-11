import io.guthub.unknown.convention.extension.setNamespace

plugins {
    id("convention.android.feature")
}

android {
    setNamespace("feature.main")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}