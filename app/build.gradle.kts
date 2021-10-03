plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = ConfigData.compileSdkVersion
    buildToolsVersion = ConfigData.buildToolsVersion

    defaultConfig {
        applicationId = ConfigData.applicationId
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    flavorDimensions("version")
    productFlavors {
        create("development") {
            dimension = "version"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            resValue("string", "app_name", "Development Coinhako Assignment")
            buildConfigField("String", "BASE_URL", "\"https://www.coinhako.com/\"")
        }
        create("staging") {
            dimension = "version"
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            resValue("string", "app_name", "Staging Coinhako Assignment")
            buildConfigField("String", "BASE_URL", "\"https://www.coinhako.com/\"")
        }
        create("production") {
            dimension = "version"
            applicationIdSuffix = ""
            versionNameSuffix = ""
            resValue("string", "app_name", "Coinhako Assignment")
            buildConfigField("String", "BASE_URL", "\"https://www.coinhako.com/\"")
        }
    }

    buildFeatures {
        dataBinding = true
    }
}

kapt {
    correctErrorTypes = true
    javacOptions {
        // These options are normally set automatically via the Hilt Gradle plugin, but we
        // set them manually to workaround a bug in the Kotlin 1.5.20
        option("-Adagger.fastInit=ENABLED")
        option("-Adagger.hilt.android.internal.disableAndroidSuperclassValidation=true")
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(Dependencies.kotlinStdlib)
    implementation(Dependencies.coreKTX)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.swipeRefreshLayout)

    implementation(Dependencies.lifecycleViewModelKTX)
    implementation(Dependencies.lifecycleLiveDataKTX)

    implementation(Dependencies.coroutinesAndroid)
    implementation(Dependencies.coroutinesCore)

    implementation(Dependencies.gson)

    implementation(Dependencies.rxAndroid)
    implementation(Dependencies.rxjava)
    implementation(Dependencies.rxKotlin)

    implementation(Dependencies.roundedImageView)

    implementation(Dependencies.glide)
    annotationProcessor(Dependencies.glideCompiler)

    implementation(Dependencies.shimmer)

    implementation(Dependencies.navigationFragment)
    implementation(Dependencies.navigationUI)

    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltAndroidCompiler)

    testImplementation(Dependencies.jUnit)
    androidTestImplementation(Dependencies.jUnitExt)
    androidTestImplementation(Dependencies.expressoCore)
}
