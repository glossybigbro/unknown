pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

//https://issuetracker.google.com/issues/328871352
//gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "unknown"
include(":app")

// core
include(
    ":core:data",
    ":core:data-api",
    ":core:datastore",
    ":core:designsystem",
    ":core:domain",
    ":core:testing",
    ":core:ui",
)

// Feature
include(
    ":feature:home",
    ":feature:main",
)
include(":core:model")
