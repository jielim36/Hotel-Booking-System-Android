plugins {
    id("com.android.application")
}

android {
    namespace = "com.hotel_booking_systems_android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hotel_booking_systems_android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures{
        viewBinding=true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildToolsVersion = "33.0.1"
}

val room_version = "2.5.2"
val navigation_version = "2.7.4"

dependencies {


    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // room: for database
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    implementation("androidx.navigation:navigation-fragment:$navigation_version")
    implementation("androidx.navigation:navigation-ui:$navigation_version")

    //circular image:github:https://github.com/hdodenhof/CircleImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")
}