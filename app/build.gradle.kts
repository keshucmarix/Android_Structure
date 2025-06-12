plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hilt)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.services)
    alias(libs.plugins.sonarqube)
}


val HOST: String by project
val SCHEME: String by project
val PORT: String by project
val PATH_SEGMENT: String by project
android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "com.app"

    defaultConfig {
        applicationId = "com.app"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        resValue("string", "HOST", HOST)
        resValue("string", "SCHEME", SCHEME)
        resValue("string", "PORT", PORT)
        resValue("string", "PATH_SEGMENT", PATH_SEGMENT)
    }

//    signingConfigs {
//        // Important: change the keystore for a production deployment
//        val userKeystore = File(System.getProperty("user.home"), ".android/debug.keystore")
//        val localKeystore = rootProject.file("debug_2.keystore")
//        val hasKeyInfo = userKeystore.exists()
//        create("release") {
//            storeFile = if (hasKeyInfo) userKeystore else localKeystore
//            storePassword = if (hasKeyInfo) "android" else System.getenv("compose_store_password")
//            keyAlias = if (hasKeyInfo) "androiddebugkey" else System.getenv("compose_key_alias")
//            keyPassword = if (hasKeyInfo) "android" else System.getenv("compose_key_password")
//        }
//    }

    buildTypes {
        getByName("debug") {

        }

        getByName("release") {
            isMinifyEnabled = false
           // signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }

    // Tests can be Robolectric or instrumented tests
    sourceSets {
        val sharedTestDir = "src/sharedTest/java"
        getByName("test") {
            java.srcDir(sharedTestDir)
        }
        getByName("androidTest") {
            java.srcDir(sharedTestDir)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kapt {
        correctErrorTypes = true
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    packaging.resources {
        excludes += "/META-INF/AL2.0"
        excludes += "/META-INF/LGPL2.1"
    }

    sonarqube {
        properties {
            property("sonar.projectKey", "Android-Architecture")
            property("sonar.projectName", "Android-Architecture")
            property("sonar.host.url", "http://203.109.113.153:9000")
            property("sonar.login", "sqp_5fdd6887be37a7fbc3ed677377a64e65fa85576b")
            property("sonar.sources", listOf("src/main/java")) // ✅ Must be a list, not a String!
            property("sonar.sourceEncoding", "UTF-8")
            property("sonar.java.binaries", listOf("build")) // Optional but helps with analysis
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "17"
}

dependencies {

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.play.services.tagmanager.v4.impl)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.ext.junit)


    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.android.testing)

    implementation(libs.ssp)
    implementation(libs.sdp)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.appcompat)
    // implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.runtime)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.recyclerview)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)
    implementation(libs.glide)
    implementation(libs.google.android.material)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.config)
    implementation(libs.firebase.analytics)
    implementation(libs.androidx.lifecycle.livedata.ktx)

}