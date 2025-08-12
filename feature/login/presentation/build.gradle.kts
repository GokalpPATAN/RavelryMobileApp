plugins {
    id("com.patan.android.feature")
    id("com.patan.android.library.compose")
    id("com.patan.android.sub.hilt")
}

android {
    namespace =  "com.patan.feature.login.presentation"
    hilt.enableAggregatingTask = true
}

dependencies{

    // Projects - Core
    implementation(projects.core.common)
    implementation(libs.javax.inject)
    implementation(projects.feature.login.domain)
}