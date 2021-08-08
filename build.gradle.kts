// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0-alpha05")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38")
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
