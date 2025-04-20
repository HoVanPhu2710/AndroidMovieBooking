plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.navigation.safeargs)  // Sử dụng alias từ libs.versions.toml
}

android {
    namespace = "com.client.androidmoviebooking"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.client.androidmoviebooking"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.inappmessaging.display)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.gson)
    implementation(libs.picasso)
    implementation(libs.dagger)
    annotationProcessor(libs.dagger.compiler)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.glide)
    annotationProcessor(libs.glideCompiler)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
}