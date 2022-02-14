import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.robertlevonyan.composable.newsapp"
        minSdk = 26
        targetSdk = 32
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
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
        kotlinCompilerExtensionVersion = "1.2.0-alpha03"
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0-native-mt")
    implementation("io.ktor:ktor-client-core:1.6.3")
    implementation("io.ktor:ktor-client-android:1.6.3")
    implementation("io.ktor:ktor-client-gson:1.6.3")

    implementation("com.google.accompanist:accompanist-flowlayout:0.23.0")
    implementation("com.google.accompanist:accompanist-insets:0.23.0")
    implementation("com.google.accompanist:accompanist-pager:0.18.0")
    implementation("com.google.android.material:material:1.6.0-alpha02")
    implementation("com.google.dagger:hilt-android:2.40.5")
    kapt("com.google.dagger:hilt-android-compiler:2.40.5")

    implementation("androidx.activity:activity-compose:1.5.0-alpha02")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.navigation:navigation-compose:2.5.0-alpha02")
    implementation("androidx.paging:paging-compose:1.0.0-alpha14")

    implementation("androidx.compose.compiler:compiler:1.2.0-alpha03")
    implementation("androidx.compose.ui:ui:1.2.0-alpha03")
    implementation("androidx.compose.material:material:1.2.0-alpha03")
    implementation("androidx.compose.ui:ui-tooling:1.2.0-alpha03")

    implementation("io.coil-kt:coil-compose:1.4.0")

    implementation("com.airbnb.android:lottie-compose:4.1.0")
    implementation("com.robertlevonyan.compose:materialchip:3.0.2")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.0-alpha03")
}
