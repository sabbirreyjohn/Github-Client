plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlin.coroutines)
}