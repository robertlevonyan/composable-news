import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.9.22"
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.robertlevonyan.composable.newsapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            val localProperties = Properties()
            localProperties.load(project.rootProject.file("local.properties").inputStream())

            val newsKey = localProperties.getProperty("NEWS_KEY")
            buildConfigField("String", "NEWS_KEY", newsKey)

            val weatherKey = localProperties.getProperty("WEATHER_KEY")
            buildConfigField("String", "WEATHER_KEY", weatherKey)
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            val localProperties = Properties()
            localProperties.load(project.rootProject.file("local.properties").inputStream())

            val newsKey = localProperties.getProperty("NEWS_KEY")
            buildConfigField("String", "NEWS_KEY", newsKey)

            val weatherKey = localProperties.getProperty("WEATHER_KEY")
            buildConfigField("String", "WEATHER_KEY", weatherKey)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs.toMutableList().addAll(
            listOf(
                "-Xallow-jvm-ir-dependencies",
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true"
            )
        )
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    namespace = "com.robertlevonyan.composable.newsapp"
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
    implementation("io.ktor:ktor-client-core:2.3.8")
    implementation("io.ktor:ktor-client-android:2.3.5")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")

    implementation("com.google.accompanist:accompanist-flowlayout:0.23.0")
    implementation("com.google.accompanist:accompanist-insets:0.23.0")
    implementation("com.google.accompanist:accompanist-pager:0.18.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.49")

    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.paging:paging-compose:3.3.0-alpha03")

    implementation("androidx.compose.compiler:compiler:1.5.10")
    implementation("androidx.compose.ui:ui:1.6.2")
    implementation("androidx.compose.material:material:1.6.2")
    implementation("androidx.compose.ui:ui-tooling:1.6.2")

    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation("com.airbnb.android:lottie-compose:6.1.0")
    implementation("com.robertlevonyan.compose:materialchip:3.0.6")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.2")
}
