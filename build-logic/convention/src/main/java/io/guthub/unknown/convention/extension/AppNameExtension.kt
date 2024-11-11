package io.guthub.unknown.convention.extension

import org.gradle.api.Project

fun Project.setNamespace(name: String) {
    androidExtension.apply {
        namespace = "io.github.unknown.$name"
    }
}