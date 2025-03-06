
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("com.google.devtools.ksp")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.nexus"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.nexus"
        minSdk = 24
        targetSdk = 35
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
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Navigation Compose
    implementation (libs.androidx.navigation.compose)
    //noinspection UseTomlInstead
    implementation("androidx.compose.material:material:1.7.7")

    implementation(platform(libs.androidx.compose.bom.v20250101))

    debugImplementation(libs.ui.tooling)
    implementation(libs.ui.tooling.preview)

    //Room DataBase
    implementation(libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    // Import the Firebase BoM
    //noinspection UseTomlInstead
    implementation(platform(libs.firebase.bom))
    implementation (libs.firebase.auth)
    implementation (libs.firebase.firestore)
    implementation(libs.firebase.analytics)
    implementation(libs.google.firebase.auth)

    //Google Icon Packs
    implementation(libs.androidx.material.icons.extended)

    // Koin para Android (inclui ViewModel e compatibilidade com Jetpack Compose)
    //noinspection UseTomlInstead
    implementation ("io.insert-koin:koin-android:3.4.0")
    // Koin para Jetpack Compose (se necessário)
    implementation (libs.koin.androidx.compose)

    // Koin para testes
    testImplementation (libs.koin.test)

    //Material Icons
    implementation(libs.material.icons.extended)

    //Data Store
    implementation (libs.androidx.datastore.preferences)

    //Google Crypto
    implementation (libs.androidx.security.crypto)

    // JUnit para testes unitários
    testImplementation(libs.junit)

    // Kotlinx Coroutines Test para testar corrotinas e fluxos
    testImplementation(libs.kotlinx.coroutines.test)

    // AndroidX Core Testing para testar componentes do Android (por exemplo, ViewModel)
    testImplementation(libs.androidx.core.testing)

    // Framework de Mock (MockK, por exemplo)
    testImplementation(libs.mockk)

    // Biblioteca de asserts, como o Truth
    testImplementation(libs.truth)

    //Biometric API
    implementation (libs.androidx.biometric.v120alpha05)

    //AppCompat
    implementation(libs.androidx.appcompat)

}