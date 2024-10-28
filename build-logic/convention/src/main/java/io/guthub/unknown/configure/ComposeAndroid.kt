package io.guthub.unknown.configure

import io.guthub.unknown.extension.androidExtension
import io.guthub.unknown.extension.libs
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

internal fun Project.configureComposeAndroid() {
    pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

    androidExtension.apply {
        applyComposeDependencies()

        afterEvaluate {
            checkComposeConfiguration()
        }
    }

    configureComposeCompilerSettings()
}

private fun Project.applyComposeDependencies() {
    dependencies {
        val bom = libs.findLibrary("androidx-compose-bom").orElseThrow {
            throw IllegalArgumentException("Unable to find the compose-bom library.")
        }
        add("implementation", platform(bom))
        add("androidTestImplementation", platform(bom))

        add("implementation", libs.findLibrary("androidx.compose.material3").get())
        add("implementation", libs.findLibrary("androidx.compose.ui").get())
        val toolingPreview = libs.findLibrary("androidx.compose.ui.tooling.preview").get()
        val dependencyConfig = if (pluginManager.hasPlugin("com.android.library")) "compileOnly" else "implementation"
        add(dependencyConfig, toolingPreview)

        add("androidTestImplementation", libs.findLibrary("androidx.test.ext").get())
        add("androidTestImplementation", libs.findLibrary("androidx.test.espresso.core").get())
        add("androidTestImplementation", libs.findLibrary("androidx.compose.ui.test").get())
        add("debugImplementation", libs.findLibrary("androidx.compose.ui.tooling").get())
        add("debugImplementation", libs.findLibrary("androidx.compose.ui.test.manifest").get())
    }
}

private fun Project.checkComposeConfiguration() {
    androidExtension.apply {
        // Deprecated: buildFeatures.compose 사용 시 예외 발생
        if (buildFeatures.compose == true) {
            throw GradleException(
                """
                buildFeatures.compose is deprecated. Please remove it.
                Add the following to the plugins block:
                plugins {
                    alias(libs.plugins.kotlin.compose)
                }
                """.trimIndent()
            )
        }

        // Deprecated: composeOptions.kotlinCompilerExtensionVersion 사용 시 예외 발생
        if (composeOptions.kotlinCompilerExtensionVersion != null) {
            throw GradleException("composeOptions.kotlinCompilerExtensionVersion is deprecated. Please remove it.")
        }
    }
}

private fun Project.configureComposeCompilerSettings() {
    extensions.configure<ComposeCompilerGradlePluginExtension> {
        // 헬퍼 함수: Gradle 속성 값이 true인 경우에만 해당 동작을 수행
        // flatMap을 사용하여 Gradle 속성의 값을 평가하고, true일 경우에만 처리합니다.
        fun Provider<String>.onlyIfTrue() = flatMap { provider { it.takeIf(String::toBoolean) } }

        // 헬퍼 함수: 루트 프로젝트 디렉토리를 기준으로 상대 경로를 얻는 함수
        // 프로젝트 디렉토리 경로를 루트 디렉토리 기준으로 변환하여, 해당 경로에서 파일을 찾습니다.
        fun Provider<*>.relativeToRootProject(dir: String) = flatMap {
            rootProject.layout.buildDirectory.dir(projectDir.toRelativeString(rootDir))
        }.map { it.dir(dir) }

        //bash
        //./gradlew assembleRelease -P enableComposeCompilerMetrics=true -P enableComposeCompilerReports=true

        // Gradle 속성 "enableComposeCompilerMetrics"가 true인 경우 컴파일러 메트릭스 경로 설정
        // ./gradlew assembleRelease -PenableComposeCompilerMetrics=true 와 같이 실행 시 활성화됩니다.
        project.providers.gradleProperty("enableComposeCompilerMetrics").onlyIfTrue()
            .relativeToRootProject("compose-metrics") // 루트 프로젝트 기준으로 "compose-metrics" 경로 설정
            .let(metricsDestination::set) // 컴파일러 메트릭스를 해당 경로에 저장하도록 설정

        // Gradle 속성 "enableComposeCompilerReports"가 true인 경우 컴파일러 리포트 경로 설정
        // ./gradlew assembleRelease -PenableComposeCompilerReports=true 와 같이 실행 시 활성화됩니다.
        project.providers.gradleProperty("enableComposeCompilerReports").onlyIfTrue()
            .relativeToRootProject("compose-reports") // 루트 프로젝트 기준으로 "compose-reports" 경로 설정
            .let(reportsDestination::set) // 컴파일러 리포트를 해당 경로에 저장하도록 설정

        // Compose 안정성 설정 파일 경로 지정
        // stabilityConfigurationFile에 안정성 관련 설정을 저장하는 파일을 지정하여 컴파일 시 적용
        stabilityConfigurationFile = rootProject.layout.projectDirectory.file("compose_compiler_config.conf")

        // Compose 컴파일러 기능 플래그 설정
        // 특정 기능들을 활성화하여 컴파일 성능을 향상시킵니다.
        // StrongSkipping: 변경되지 않은 부분을 건너뛰어 컴파일 시간을 단축
        // OptimizeNonSkippingGroups: 스킵할 수 없는 그룹을 최적화하여 성능 향상
        // IntrinsicRemember: remember 사용 시 성능 최적화
//        featureFlags.addAll(
//            ComposeFeatureFlag.StrongSkipping, // 강력한 스킵 모드 활성화
//            ComposeFeatureFlag.OptimizeNonSkippingGroups, // 스킵할 수 없는 그룹의 최적화
//            ComposeFeatureFlag.IntrinsicRemember // remember를 사용할 때 최적화
//        )
    }
}
