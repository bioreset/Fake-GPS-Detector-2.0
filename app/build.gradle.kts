plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.dariusz.fakegpsdetector"
        minSdk = 24
        targetSdk = 32
        versionCode = 10
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
        compose = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    kapt {
        correctErrorTypes = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-rc01"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0-alpha05")
    implementation("androidx.activity:activity-compose:1.5.0-rc01")

    implementation("com.google.android.gms:play-services-maps:18.0.2")
    implementation("com.google.android.gms:play-services-location:20.0.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0-rc01")

    implementation("com.squareup.moshi:moshi:1.13.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.2")

    implementation("com.google.dagger:hilt-android:2.42")
    kapt("com.google.dagger:hilt-android-compiler:2.42")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("androidx.navigation:navigation-compose:2.5.0-rc02")

    implementation("com.google.maps.android:maps-compose:2.2.1")

    implementation("androidx.compose.ui:ui:1.2.0-rc01")
    implementation("androidx.compose.foundation:foundation:1.2.0-rc01")
    implementation("androidx.compose.material3:material3:1.0.0-alpha13")
    implementation("androidx.compose.material:material-icons-extended:1.2.0-rc01")
    implementation("androidx.compose.ui:ui-tooling:1.2.0-rc01")
    implementation("com.google.android.material:compose-theme-adapter-3:1.0.11")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}