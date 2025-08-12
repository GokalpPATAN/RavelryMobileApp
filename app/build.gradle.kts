import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Properties
plugins {
    id("com.patan.android.application")
    id("com.patan.android.application.compose")
    id("com.patan.android.sub.hilt")
    id("com.patan.android.firebase")
}

android {
    namespace = defaultSecrets().getProperty("APPLICATION_ID")
    compileSdk = defaultSecrets().getProperty("COMPILE_SDK").toInt()

    defaultConfig {
        applicationId = defaultSecrets().getProperty("APPLICATION_ID")
        minSdk = defaultSecrets().getProperty("MIN_SDK").toInt()
        targetSdk = defaultSecrets().getProperty("TARGET_SDK").toInt()
        versionCode = computedVersionCode()
        versionName = defaultSecrets().getProperty("VERSION_NAME")
        setProperty("archivesBaseName", "$namespace-$versionName-$versionCode-${gitCommitCount()}")
        ndk {
            debugSymbolLevel = "FULL"
        }

        testInstrumentationRunner = defaultSecrets().getProperty("TEST_RUNNER")
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {

    }

    buildTypes {
        debug {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            isMinifyEnabled = defaultSecrets().getProperty("MINIFY_ENABLED_DEBUG").toBoolean()
            isDebuggable = true
            versionNameSuffix = ".${gitCommitCount()}-SNAPSHOT"
        }
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            isMinifyEnabled = defaultSecrets().getProperty("MINIFY_ENABLED_RELEASE").toBoolean()
            isShrinkResources = true
            isDebuggable = false
            versionNameSuffix = ".${gitCommitCount()}"
            signingConfig = signingConfigs.findByName("release")
        }
    }

    flavorDimensions += "version"
    productFlavors {
        create("beta") {
            dimension = "version"
            versionNameSuffix = "-beta"
        }
        create("prod") {
            dimension = "version"
        }
    }

    buildFeatures.buildConfig = true
    hilt.enableAggregatingTask = true

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Projects - Core
    implementation(projects.core.designsystem)
    implementation(projects.core.common)
    implementation(projects.core.commonUi)
    implementation(projects.core.navigation)

    // Projects - Feature
    implementation(projects.feature.home.presentation)
    implementation(projects.feature.login.presentation)
    implementation(projects.feature.login.data)

    // AndroidX
    implementation(libs.androidx.core.splashscreen)

    // Compose
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
}

fun gitCommitCount(): String {
    val os = org.apache.commons.io.output.ByteArrayOutputStream()
    project.exec {
        commandLine = "git rev-list HEAD --count".split(" ")
        standardOutput = os
    }
    return String(os.toByteArray()).trim()
}

/**
 * computedVersionCode()
 * do not name this to getVersionCode. getVersionCode conflicts with the automatic getter of versionCode
 * version code is an int a value between 0 and max int value 2147483647 is expected.
 * This function returns at int in yyyMMddHH format
 * For example, 2022061121 for 11 June 2022 between 21:00 to 21:59
 * This gives a new versioncode for every different hour of day and same code within same hour of hour of day
 * Max int value is 2147483647. So after year 2147 it will overflow to -ve values.
 * max value in year 2147 will be 2147121223 so Lot of scope of manually incrementing up-to 2147483647  will be there.
 * @return an int corresponding to current hour in yyyyMMddHH format
 */
fun computedVersionCode(): Int {
    val date = Date()
    val formattedDate = SimpleDateFormat("yyyyMMddHH", Locale.getDefault()).format(date)
    return formattedDate.toLong().toInt()
}

// Secrets
fun defaultSecrets(): Properties {
    val keystoreFile = project.rootProject.file("secrets.properties")
    val properties = Properties()
    properties.load(keystoreFile.inputStream())
    return properties
}
