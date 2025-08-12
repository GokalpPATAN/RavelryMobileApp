plugins {
    id("com.patan.android.feature")
    id("com.patan.android.sub.hilt")
    id("com.patan.android.firebase")
    id("com.patan.android.library.compose")
}

android {
    namespace =  "com.patan.feature.login.data"
    hilt.enableAggregatingTask = true
}

dependencies{

    // Projects - Core
    implementation(projects.core.common)
    implementation(projects.core.firebase)
    implementation(projects.feature.login.domain)
    implementation(libs.googleid)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.auth)
    implementation(libs.datastore.preferences)
    implementation(libs.security.crypto)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlinx)
    implementation(libs.kotlinx.serialization.json)
}