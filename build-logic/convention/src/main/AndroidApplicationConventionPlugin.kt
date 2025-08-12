import com.android.build.api.dsl.ApplicationExtension
import com.patan.app.extensions.configureKotlinAndroid
import com.patan.app.extensions.defaultSecrets
import com.patan.app.extensions.registerPrePushTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.patan.sub.ktlint")
                apply("com.patan.sub.detekt")
                apply("com.patan.sub.spotless")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = rootProject.defaultSecrets().getProperty("TARGET_SDK").toInt()
                compileSdk = rootProject.defaultSecrets().getProperty("COMPILE_SDK").toInt()
            }
            registerPrePushTask()
        }
    }
}
