import com.patan.app.utils.androidTestImplementation
import com.patan.app.utils.implementation
import com.patan.app.utils.libs
import com.patan.app.utils.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.patan.android.library")
                apply("com.patan.android.sub.hilt")
                apply("com.patan.sub.spotless")
                apply("com.patan.sub.ktlint")
                apply("com.patan.sub.detekt")
            }

            dependencies {
                //Projects
                implementation(project(":core:common"))
                implementation(project(":core:common-ui"))
                implementation(project(":core:navigation"))

                //Test
                testImplementation(kotlin("test"))
                androidTestImplementation(kotlin("test"))

                //Libs
                implementation(libs.findLibrary("timber").get())
                implementation(libs.findLibrary("androidx.navigation.compose").get())
                implementation(libs.findLibrary("androidx.hilt.navigation.compose").get())
                implementation(libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                implementation(libs.findLibrary("androidx.lifecycle.viewModelCompose").get())

                implementation(libs.findLibrary("kotlinx.coroutines.android").get())
                implementation(libs.findLibrary("kotlinx.collections.immutable").get())
            }
        }
    }
}
