plugins {
    id("com.patan.android.library")
    id("com.patan.android.library.design.system.compose")
}

android {
    namespace = "com.patan.designsystem"
    buildFeatures.compose = true
}

dependencies {

    // Project

    // AndroidX
    implementation(libs.androidx.core.ktx)

    // Kotlin
    implementation(libs.kotlin.stdlib)

    // Compose
    api(libs.androidx.activity.compose)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    debugApi(libs.androidx.compose.ui.tooling)
}