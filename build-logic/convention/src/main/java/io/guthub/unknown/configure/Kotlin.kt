package io.guthub.unknown.configure

import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtension

inline fun <reified T : KotlinTopLevelExtension> Project.configureKotlin() = configure<T> {
    // Treat all Kotlin warnings as errors (disabled by default)
    // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
    val warningsAsErrors: String? by project
    when (this) {
        is KotlinAndroidProjectExtension -> compilerOptions
        is KotlinJvmProjectExtension -> compilerOptions
        else -> TODO("Unsupported project extension $this ${T::class}")
    }.apply {
        jvmTarget = JvmTarget.JVM_17
        allWarningsAsErrors.set(warningsAsErrors.toBoolean())
        freeCompilerArgs.set(
            freeCompilerArgs.get() + listOf(
                // 문자열 결합을 StringBuilder가 아닌 인라인 방식으로 처리하여 성능 최적화
                "-Xstring-concat=inline",
                // Kotlin의 실험적 또는 제한된 API를 사용하기 위해 명시적으로 옵트인하는 옵션
                "-opt-in=kotlin.RequiresOptIn",
                // kotlinx.coroutines 라이브러리의 실험적 코루틴 API를 사용하기 위해 옵트인하는 옵션
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
            )
        )
    }
}