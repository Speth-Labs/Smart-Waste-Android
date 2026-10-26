import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

val lanConfigFile = rootProject.file("app/lan.properties")
val lanConfig = Properties().apply {
    if (lanConfigFile.isFile) lanConfigFile.inputStream().use { load(it) }
}
val smartWasteApiHost = providers.gradleProperty("smartWasteApiHost")
    .orElse(lanConfig.getProperty("apiHost") ?: "10.0.2.2").get()
val smartWasteVersionCode = providers.gradleProperty("smartWasteVersionCode")
    .orElse(lanConfig.getProperty("versionCode") ?: "1").get().toInt()
val smartWasteVersionName = providers.gradleProperty("smartWasteVersionName")
    .orElse(lanConfig.getProperty("versionName") ?: "1.0").get()

android {
    namespace = "com.uoi.pratikumwithbunafla"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.uoi.pratikumwithbunafla"
        minSdk = 24
        // Android 17 (SDK 37) requires ACCESS_LOCAL_NETWORK for LAN requests.
        // SDK 36 and lower still receive local-network access through INTERNET.
        targetSdk = 36
        versionCode = smartWasteVersionCode
        versionName = smartWasteVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField(
            "String",
            "SMART_WASTE_API_URL",
            "\"http://$smartWasteApiHost:8080/Waste2/backend-php/index.php\""
        )
        buildConfigField(
            "String",
            "SMART_WASTE_UPDATE_URL",
            "\"http://$smartWasteApiHost:8080/Waste2/updates/latest.json\""
        )
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

}

dependencies {
    implementation(libs.navigation.compose)
    implementation(libs.coroutines.android)
    implementation(libs.compose.icons.font.awesome)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
