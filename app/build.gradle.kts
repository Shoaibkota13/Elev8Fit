plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.fitness.elev8fit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fitness.elev8fit"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
dependencies {
    // Core AndroidX Libraries
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.0")

    // Navigation
    val nav_version = "2.8.5"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.1")
    implementation ("com.google.firebase:firebase-dynamic-links-ktx:21.2.0")
    implementation("com.google.firebase:firebase-messaging-ktx:24.1.0")

    // Google Play Services and Identity
    implementation("androidx.credentials:credentials:1.2.2")
    implementation("androidx.credentials:credentials-play-services-auth:1.2.2")
    implementation("com.google.android.gms:play-services-auth:20.0.1")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Coil for Image Loading
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Glide for Image Loading
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")
    kapt("com.github.bumptech.glide:compiler:4.15.1")

    // Hilt for Dependency Injection
    implementation("com.google.dagger:hilt-android:2.54")
    kapt("com.google.dagger:hilt-compiler:2.54")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Permissions
    implementation("com.google.accompanist:accompanist-permissions:0.37.0")

    // Facebook SDK
    implementation("com.facebook.android:facebook-android-sdk:16.0.0")

    // Amazon SDK for S3
    implementation("com.amazonaws:aws-android-sdk-s3:2.75.0")

    // Shimmer Effect
    implementation("com.valentinilk.shimmer:compose-shimmer:1.3.1")

    // Biometric Authentication
    implementation("androidx.biometric:biometric:1.4.0-alpha02")

    // Gson
    implementation("com.google.code.gson:gson:2.8.9")

    // Testing Libraries
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}


//dependencies {
//
//    implementation("androidx.core:core-ktx:1.15.0")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
//    implementation("androidx.activity:activity-compose:1.9.3")
//    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-graphics")
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    implementation("androidx.compose.material3:material3")
//    implementation("com.google.firebase:firebase-auth-ktx:23.1.0")
//    implementation("com.google.firebase:firebase-auth:23.1.0")
//    implementation("com.google.firebase:firebase-firestore-ktx:25.1.1")
//    implementation("com.google.firebase:firebase-messaging-ktx:24.1.0")
//
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.2.1")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
//    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//    debugImplementation("androidx.compose.ui:ui-tooling")
//    debugImplementation("androidx.compose.ui:ui-test-manifest")
//    val nav_version = "2.8.5"
//
//    implementation("androidx.navigation:navigation-compose:$nav_version")
//
//
//    //imagecoil
//    implementation("io.coil-kt:coil-compose:2.4.0")
//    implementation("androidx.compose.runtime:runtime-livedata:1.5.0")
//    //view model
//    val lifecycle_version = "2.8.7"
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
//    //google
//    implementation ("androidx.credentials:credentials:1.2.2")
//    implementation ("androidx.credentials:credentials-play-services-auth:1.2.2")
//    implementation ("com.google.android.gms:play-services-auth:20.0.1")
//    implementation ("com.google.android.libraries.identity.googleid:googleid:1.1.1")
//    //firebase
//    implementation("com.google.firebase:firebase-firestore")
//    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
//    implementation ("com.google.firebase:firebase-auth-ktx:21.0.0")
//
//    //retrofit
//    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Use 2.9.0 for the latest stable
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    // Kotlin Coroutines
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
//    //glide
//    implementation("com.github.bumptech.glide:glide:4.15.1") // Core Glide library
//    implementation("com.github.bumptech.glide:compose:1.0.0-beta01") // Glide Compose integration
//    kapt("com.github.bumptech.glide:compiler:4.15.1")
//
//    // hilwith kotlin
//    implementation ("com.google.dagger:hilt-android:2.54")
//    kapt ("com.google.dagger:hilt-compiler:2.54")
//    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
//    //implementation ("com.facebook.android:facebook-android-sdk:latest.release")
//    implementation("com.facebook.android:facebook-android-sdk:16.0.0")
////    import com.facebook.FacebookSdk;
////    import com.facebook.appevents.AppEventsLogger;
//    implementation ("androidx.datastore:datastore-preferences:1.0.0")
//   //image selector
//    implementation("com.google.accompanist:accompanist-permissions:0.37.0")
//    implementation ("androidx.compose.foundation:foundation:1.1.0")
//    //amazon sdk
//    implementation ("com.amazonaws:aws-android-sdk-s3:2.75.0")
//    //shimmering effect
//    implementation("com.valentinilk.shimmer:compose-shimmer:1.3.1")
//    implementation("androidx.biometric:biometric:1.1.0")
//    implementation ("com.google.code.gson:gson:2.8.9")
//   implementation("com.squareup.okhttp3:okhttp:4.9.3")
//
//
//
//
//}

hilt {
    enableTransformForLocalTests = true
}
