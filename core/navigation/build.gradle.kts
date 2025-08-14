plugins {
    id("com.patan.android.library")
}

android {
    namespace = "com.patan.navigation"
}

dependencies {
    // Projects

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
}