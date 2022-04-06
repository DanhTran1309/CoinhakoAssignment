plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = ConfigData.compileSdkVersion
    buildToolsVersion = ConfigData.buildToolsVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    setPublishNonDefault(true)
    flavorDimensions.add("version")
    productFlavors {
        create("development") {
            dimension = "version"
            buildConfigField("String", "BASE_URL", "\"https://www.coinhako.com/\"")
        }
        create("staging") {
            dimension = "version"
            buildConfigField("String", "BASE_URL", "\"https://www.coinhako.com/\"")
        }
        create("production") {
            dimension = "version"
            buildConfigField("String", "BASE_URL", "\"https://www.coinhako.com/\"")
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))

    implementation(Dependencies.kotlinStdlib)
    implementation(Dependencies.coreKTX)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material)

    implementation(Dependencies.coroutinesAndroid)
    implementation(Dependencies.coroutinesCore)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitConverterGson)

    implementation(Dependencies.gson)

    implementation(Dependencies.roomRuntime)
    kapt(Dependencies.roomCompiler)
    implementation(Dependencies.roomKTX)

    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltAndroidCompiler)

    testImplementation(Dependencies.jUnit)
    androidTestImplementation(Dependencies.jUnitExt)
    androidTestImplementation(Dependencies.expressoCore)
}
