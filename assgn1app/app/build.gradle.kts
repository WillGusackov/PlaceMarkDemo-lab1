
import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.secrets.gradle.plugin)
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        load(FileInputStream(file))
    }
}
val placesApiKey = localProperties.getProperty("PLACES_API_KEY", "DEFAULT_API_KEY")

android {
    namespace = "com.example.assgn1_app"
    compileSdk {
        version = release(37)
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    defaultConfig {
        applicationId = "com.example.assgn1_app"
        minSdk = 24
        targetSdk = 34
        versionCode = 2
        versionName = "2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["PLACES_API_KEY"] = placesApiKey
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
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.material)
    testImplementation(libs.androidx.core)
    testImplementation(libs.androidx.espresso.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.google.maps)
    implementation(libs.google.place)
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("org.robolectric:robolectric:4.14.1")
    implementation("com.jakewharton.timber:timber:5.0.1")


}