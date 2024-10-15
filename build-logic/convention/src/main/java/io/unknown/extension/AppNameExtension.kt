package io.unknown.extension

import org.gradle.api.Project

fun Project.setNamespace(name: String) {
    androidExtension.apply {
        namespace = "io.unknown.$name"
    }
}