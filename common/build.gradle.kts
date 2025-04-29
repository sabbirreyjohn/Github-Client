plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.protobuf)
    id(libs.plugins.gradle.secrets.get().pluginId)
    kotlin(libs.plugins.kotlinx.serialization.get().pluginId)
    alias(libs.plugins.ksp)
}

android {
    namespace = "xyz.androidrey.githubclient.common"
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(projects.network)
    implementation(projects.theme)

    implementation(libs.kotlinx.serialization)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.ksp)
    implementation(libs.hilt.compose)
    ksp(libs.hilt.compiler.ksp)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    implementation(libs.ktor.client.core)
}