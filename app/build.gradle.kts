plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.dariusz.fakegpsdetector"
        minSdk = 24
        targetSdk = 30
        versionCode = 8
        versionName = "0.8"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    kapt {
        correctErrorTypes = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.0-beta09"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.5.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("androidx.activity:activity-ktx:1.3.0-beta02")
    implementation("com.google.android.material:material:1.4.0-rc01")

    implementation("com.google.android.gms:play-services-maps:17.0.1")
    implementation("com.google.android.gms:play-services-location:18.0.0")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")

    implementation("com.squareup.moshi:moshi:1.12.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.12.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")

    implementation("com.google.dagger:hilt-android:2.37")
    kapt("com.google.dagger:hilt-android-compiler:2.37")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    implementation("com.google.maps.android:maps-ktx:3.1.0")

    implementation("androidx.compose.ui:ui:1.0.0-beta09")
    implementation("androidx.compose.foundation:foundation:1.0.0-beta09")
    implementation("androidx.compose.material:material:1.0.0-beta09")
    implementation("androidx.compose.runtime:runtime-livedata:1.0.0-beta09")
    implementation("com.google.android.material:compose-theme-adapter:1.0.0-beta09")
    implementation("androidx.activity:activity-compose:1.3.0-beta02")
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha03")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07")
    implementation("androidx.compose.ui:ui-tooling:1.0.0-beta09")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
