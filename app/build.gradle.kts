plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version "1.4.30"
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.kyant.pixelmusic"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode(1)
        versionName = "0.1.0-alpha05"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }
    kotlinOptions {
        jvmTarget = "11"
        useIR = true
        freeCompilerArgs = freeCompilerArgs + listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.0-SNAPSHOT"
    }
}

dependencies {
    val ktor = "1.5.1"
    val compose = "1.0.0-SNAPSHOT"
    val exoPlayer = "2.12.3"

    implementation(project(":iNimate"))
    implementation(kotlin("reflect", "1.4.30"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
    implementation("io.ktor:ktor-client-cio:$ktor")
    implementation("io.ktor:ktor-client-serialization:$ktor")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-SNAPSHOT")
    implementation("androidx.appcompat:appcompat:1.3.0-SNAPSHOT")
    implementation("androidx.core:core-ktx:1.5.0-SNAPSHOT")
    implementation("androidx.media:media:1.3.0-SNAPSHOT")
    implementation("androidx.palette:palette:1.0.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.compose.material:material:$compose")
    implementation("androidx.compose.material:material-icons-extended:$compose")
    implementation("androidx.compose.runtime:runtime:$compose")
    implementation("androidx.compose.ui:ui:$compose")
    implementation("androidx.compose.ui:ui-tooling:$compose")
    implementation("androidx.compose.ui:ui-util:$compose")
    implementation("androidx.activity:activity-compose:1.3.0-SNAPSHOT")
    implementation("androidx.navigation:navigation-compose:1.0.0-SNAPSHOT")
    implementation("com.google.android.exoplayer:exoplayer-core:$exoPlayer")
    implementation("com.google.android.exoplayer:extension-mediasession:$exoPlayer")
    implementation("io.coil-kt:coil:1.1.1")
    implementation("com.github.lincollincol:Amplituda:1.5")
    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.3-alpha04")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0-alpha04")
}