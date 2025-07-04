plugins {
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.google.services) apply false  // Add this line
    alias(libs.plugins.firebase.crashlytics) apply false  // Add this line
}



