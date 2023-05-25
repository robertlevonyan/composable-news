import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.8.20"
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.robertlevonyan.composable.newsapp"
        minSdk = 26
        targetSdk = 33
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
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    namespace = "com.robertlevonyan.composable.newsapp"
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("io.ktor:ktor-client-core:2.3.0")
    implementation("io.ktor:ktor-client-android:2.3.0")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")

    implementation("com.google.accompanist:accompanist-flowlayout:0.23.0")
    implementation("com.google.accompanist:accompanist-insets:0.23.0")
    implementation("com.google.accompanist:accompanist-pager:0.18.0")
    implementation("com.google.android.material:material:1.9.0-rc01")
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-android-compiler:2.45")

    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.navigation:navigation-compose:2.7.0-alpha01")
    implementation("androidx.paging:paging-compose:1.0.0-alpha19")

    implementation("androidx.compose.compiler:compiler:1.4.7")
    implementation("androidx.compose.ui:ui:1.5.0-beta01")
    implementation("androidx.compose.material:material:1.5.0-beta01")
    implementation("androidx.compose.ui:ui-tooling:1.5.0-beta01")

    implementation("io.coil-kt:coil-compose:2.2.2")

    implementation("com.airbnb.android:lottie-compose:6.0.0")
    implementation("com.robertlevonyan.compose:materialchip:3.0.5")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.0-beta01")
}
