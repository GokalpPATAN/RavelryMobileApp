plugins{
    id("com.patan.android.library")
    id("com.patan.android.sub.hilt")
}

android{
    namespace = "com.patan.common"
    hilt.enableAggregatingTask =  true
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.network.gson)
}