import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    `kotlin-dsl`
}

group = defaultSecrets().getProperty("BUILD_LOGIC_GROUP")

java{
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies{
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.detekt.compose)
    compileOnly(libs.spotless.gradlePlugin)
    compileOnly(libs.ktlint.gradlePlugin)
    compileOnly(libs.google.services.gradlePlugin)
}

gradlePlugin {
    plugins {
        //BASE
        register("androidApplication") {
            id = "com.patan.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "com.patan.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "com.patan.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "com.patan.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibraryDesignSystemCompose") {
            id = "com.patan.android.library.design.system.compose"
            implementationClass = "AndroidLibraryComposeDesignSystemConventionPlugin"
        }
        register("androidFeature") {
            id = "com.patan.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        //SUB
        register("androidHilt") {
            id = "com.patan.android.sub.hilt"
            implementationClass = "SubAndroidHiltConventionPlugin"
        }
        register("androidKtlint") {
            id = "com.patan.sub.ktlint"
            implementationClass = "SubAndroidKtlintConventionPlugin"
        }
        register("androidSpotless") {
            id = "com.patan.sub.spotless"
            implementationClass = "SubAndroidSpotlessConventionPlugin"
        }
        register("androidDetekt") {
            id = "com.patan.sub.detekt"
            implementationClass = "SubAndroidDetektConventionPlugin"
        }

        register("androidFirebase") {
            id = "com.patan.android.firebase"
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }
    }
}


fun defaultSecrets(): Properties {
    val keystoreFile = project.rootProject.file("../secrets.properties")
    val properties = Properties()
    properties.load(keystoreFile.inputStream())
    return properties
}

