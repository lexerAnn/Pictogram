plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.hilt)


}

android {
    namespace = "com.pictogram"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pictogram"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.pictogram.HiltTestRunner"  // no 'di' in path
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", getPropertyValue("imageBaseUrl"))
            buildConfigField("String", "PIXBAY_API_KEY", getPropertyValue("pixbayKey"))
            enableUnitTestCoverage
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", getPropertyValue("imageBaseUrl"))
            buildConfigField("String", "PIXBAY_API_KEY", getPropertyValue("pixbayKey"))
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
        buildConfig = true  // This must be before any buildConfigField usage
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment.testing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //Logging
    implementation(libs.timber)

    //Navigation
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Network Requests
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)
    implementation (libs.okhttpprofiler)

    //Paging
    implementation (libs.androidx.paging.runtime.ktx)

    //Glide
    implementation (libs.glide)
    //Test

    androidTestImplementation(libs.mockito.core)
    androidTestImplementation (libs.mockito.android)
    implementation (libs.mockito.core.v531)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation (libs.androidx.fragment.testing.manifest)
    androidTestImplementation (libs.androidx.fragment.testing)
    androidTestImplementation (libs.hilt.android.testing)
    kaptAndroidTest (libs.dagger.hilt.android.compiler)
    testImplementation (libs.kotlinx.coroutines.test)







}

fun getPropertyValue(propertyName: String): String {
    val property = project.findProperty(propertyName) as String?
        ?: System.getenv(propertyName)
        ?: throw IllegalArgumentException(
            "Property '$propertyName' not found in gradle.properties or system environment"
        )

    return property.trim()
}

fun resourceName(value: String): String {
    return value
        .replace(Regex("([a-z])([A-Z])"), "$1_$2")
        .uppercase()
        .let { "GRADLE_${it}" }
}