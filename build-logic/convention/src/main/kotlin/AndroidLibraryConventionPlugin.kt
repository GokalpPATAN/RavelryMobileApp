import com.android.build.gradle.LibraryExtension
import com.patan.app.extensions.configureKotlinAndroid
import com.patan.app.extensions.defaultSecrets
import com.patan.app.utils.androidTestImplementation
import com.patan.app.utils.implementation
import com.patan.app.utils.kapt
import com.patan.app.utils.libs
import com.patan.app.utils.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("com.patan.sub.spotless")
                apply("com.patan.sub.ktlint")
                apply("com.patan.sub.detekt")
                apply("kotlin-kapt")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = rootProject.defaultSecrets().getProperty("TARGET_SDK").toInt()

            }
            dependencies {
                //Lifecycle
                implementation(libs.findLibrary("androidx-lifecycle-runtime").get())
                kapt(libs.findLibrary("androidx-lifecycle-compiler").get())
                //Test
                androidTestImplementation(kotlin("test"))
                testImplementation(kotlin("test"))
            }
        }
    }
}
