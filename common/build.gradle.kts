plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.protobuf)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
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
    implementation(projects.storage)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.compose.navigation)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation (libs.androidx.constraintlayout.compose)
    implementation(libs.kotlinx.serialization)

    implementation(libs.hilt.android)
    implementation(libs.firebase.auth)
    ksp(libs.hilt.android.ksp)
    implementation(libs.hilt.compose)
    ksp(libs.hilt.compiler.ksp)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)

    implementation(libs.onetapcompose)
    implementation(libs.androidx.datastore)

    implementation(libs.androidx.datastore.preferences)
    implementation (libs.protobuf.javalite)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.19.4"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins.create("java") {
                option("lite")
            }
        }
    }
}