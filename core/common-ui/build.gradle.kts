plugins {
    id("com.patan.android.library")
    id("com.patan.android.library.compose")
}

android {
    namespace = "com.patan.commonui"
    buildFeatures.compose = true
}

dependencies {
    // Projects

    // Projects - Core
    implementation(projects.core.designsystem)
    implementation(projects.core.common)

    // AndroidX
    implementation(libs.androidx.core.ktx)

    // Kotlin
    implementation(libs.kotlin.stdlib)
}