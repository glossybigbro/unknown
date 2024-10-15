@file:Suppress("EnumEntryName")

package io.unknown.configure

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.ProductFlavor
import io.unknown.extension.applicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

enum class FlavorDimension {
    environment
}

enum class Flavor(
    val dimension: FlavorDimension,
    val applicationIdSuffix: String = "",
    val versionNameSuffix: String = "",
) {
    dev(FlavorDimension.environment, applicationIdSuffix = ".dev", versionNameSuffix = "-dev"),
    qa(FlavorDimension.environment, applicationIdSuffix = ".qa", versionNameSuffix = "-qa"),
    prod(FlavorDimension.environment)
}

fun Project.configureFlavors(
    flavorConfigurationBlock: ProductFlavor.(flavor: Flavor) -> Unit = {},
) {
    applicationExtension.apply {
        flavorDimensions += FlavorDimension.environment.name
        productFlavors {
            Flavor.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                    flavorConfigurationBlock(this, it)

                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (it.applicationIdSuffix.isNotEmpty()) {
                            applicationIdSuffix = it.applicationIdSuffix
                        }
                        if (it.versionNameSuffix.isNotEmpty()) {
                            versionNameSuffix = it.versionNameSuffix
                        }
                    }
                }
            }
        }
    }
}

class AndroidApplicationFlavorsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<ApplicationExtension> {
                configureFlavors()
            }
        }
    }
}