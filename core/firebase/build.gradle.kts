plugins {
    id("com.patan.android.library")
    id("com.patan.android.sub.hilt")
    id("com.patan.android.firebase")
}

android {
    namespace = "com.patan.core.firebase"
    hilt.enableAggregatingTask = true

}

dependencies {

    // Projects - Core
    implementation(projects.core.common)

    // Firebase
    implementation(libs.firebase.auth)

}