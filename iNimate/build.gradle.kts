plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"
    ndkVersion = "22.0.7026061"

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode(1)
        versionName = "0.1.0-alpha09"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            ndkBuild {
                abiFilters("x86", "x86_64", "armeabi-v7a", "arm64-v8a")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
        freeCompilerArgs = freeCompilerArgs + listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.0-SNAPSHOT"
    }
    externalNativeBuild {
        ndkBuild {
            path = file("jni/Android.mk")
        }
    }
}

dependencies {
    val compose = "1.0.0-SNAPSHOT"

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-SNAPSHOT")
    implementation("androidx.core:core-ktx:1.5.0-SNAPSHOT")
    implementation("androidx.dynamicanimation:dynamicanimation-ktx:1.0.0-SNAPSHOT")
    implementation("androidx.compose.material:material:$compose")
    implementation("androidx.compose.material:material-icons-extended:$compose")
    implementation("androidx.compose.runtime:runtime:$compose")
    implementation("androidx.compose.ui:ui:$compose")
    implementation("androidx.compose.ui:ui-tooling:$compose")
    implementation("androidx.compose.ui:ui-util:$compose")
    implementation("androidx.navigation:navigation-compose:1.0.0-SNAPSHOT")
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.3-alpha04")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0-alpha04")
}