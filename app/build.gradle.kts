plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id(libs.plugins.ksp.get().pluginId)
    kotlin(libs.plugins.kotlinx.serialization.get().pluginId)
}

android {
    namespace = "xyz.androidrey.githubclient"

    defaultConfig {
        applicationId = "xyz.androidrey.githubclient"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.features.main)
    implementation(projects.common)
    implementation(projects.theme)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.kotlinx.serialization)


    implementation(libs.hilt.android)
    ksp(libs.hilt.android.ksp)
    implementation(libs.hilt.compose)
   ksp(libs.hilt.compiler.ksp)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preferences)
    implementation (libs.protobuf.javalite)

    implementation(libs.kmpauth.google) {
        exclude ("io.ktor")
    }
    implementation(libs.kmpauth.uihelper){
        exclude ("io.ktor")
    }

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}