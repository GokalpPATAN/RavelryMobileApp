plugins {
    id("com.patan.android.feature")
}

android {
    namespace =  "com.patan.feature.login.domain"
    hilt.enableAggregatingTask = true
}

dependencies{

    // Projects - Core
    implementation(projects.core.common)
    implementation(libs.javax.inject)

}