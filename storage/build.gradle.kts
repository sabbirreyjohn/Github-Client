plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "xyz.androidrey.githubclient.common"
}

dependencies {
    implementation(libs.kotlin.coroutines)
    implementation(projects.common)
}