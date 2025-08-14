import com.patan.app.extensions.defaultSecrets

plugins {
    id("com.patan.android.feature")
    id("com.patan.android.sub.hilt")
    id("com.patan.android.library.compose")
}

android {
    buildFeatures{
        buildConfig = true
    }
    namespace =  "com.patan.feature.login.data"
    hilt.enableAggregatingTask = true

    defaultConfig {
        // ⚠️ resValue'da TIRNAK KOYMA! Değeri direkt geçir.
        val sec = rootProject.defaultSecrets()
        resValue("string", "ravelry_client_id",    sec.getProperty("RAVELRY_CLIENT_ID"))
        resValue("string", "ravelry_client_secret",sec.getProperty("RAVELRY_CLIENT_SECRET"))
        resValue("string", "ravelry_redirect_uri", sec.getProperty("RAVELRY_REDIRECT_URI"))
        resValue("string", "ravelry_scopes",       sec.getProperty("RAVELRY_SCOPES"))
        resValue("string", "ravelry_auth_url",     sec.getProperty("RAVELRY_AUTH_URL"))
        resValue("string", "ravelry_token_url",    sec.getProperty("RAVELRY_TOKEN_URL"))
    }
}

dependencies{

    // Projects - Core
    implementation(projects.core.common)
    implementation(projects.feature.login.domain)
    implementation(libs.datastore.preferences)
    implementation(libs.security.crypto)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlinx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.browser)
}