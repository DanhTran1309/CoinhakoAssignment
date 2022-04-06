
object BuildPlugins {
    val android by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinGradlePlugin}" }
}

object Dependencies {
    val kotlinStdlib by lazy { "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}" }
    val coreKTX by lazy { "androidx.core:core-ktx:${Versions.coreKTX}" }
    val appcompat by lazy { "androidx.appcompat:appcompat:${Versions.appcompat}" }
    val material by lazy { "com.google.android.material:material:${Versions.material}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraint}" }
    val swipeRefreshLayout by lazy { "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe}" }
    val lifecycleViewModelKTX by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}" }
    val lifecycleLiveDataKTX by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}" }
    val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }
    val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}" }
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitConverterGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }
    val gson by lazy { "com.google.code.gson:gson:${Versions.gson}" }
    val roundedImageView by lazy { "com.makeramen:roundedimageview:${Versions.roundedImageView}" }
    val glide by lazy { "com.github.bumptech.glide:glide:${Versions.glide}" }
    val glideCompiler by lazy { "com.github.bumptech.glide:compiler:${Versions.glide}" }
    val shimmer by lazy { "com.facebook.shimmer:shimmer:${Versions.shimmer}" }
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.room}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }
    val roomKTX by lazy { "androidx.room:room-ktx:${Versions.room}" }
    val navigationFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}" }
    val navigationUI by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navigation}" }
    val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltAndroidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }

    val jUnit by lazy { "junit:junit:${Versions.jUnit}" }
    val jUnitExt by lazy { "androidx.test.ext:junit:${Versions.jUnitExt}" }
    val expressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.expressoCore}" }
}
