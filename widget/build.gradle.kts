plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("signing")
}

android {
    namespace = "com.lwons.widget.hw"
    compileSdk = 33

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.properties["GROUP"] as String
            artifactId = "hwtextureview"
            version = project.properties["VERSION_NAME"] as String

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name = "HwTextureView"
                description = project.property("POM_DESCRIPTION") as String
                url = project.property("POM_URL") as String
                licenses {
                    license {
                        name = project.property("POM_LICENCE_NAME") as String
                        url = project.property(("POM_LICENCE_URL")) as String
                    }
                }
                scm {
                    connection = project.property("POM_SCM_CONNECTION") as String
                    url = project.property("POM_SCM_URL") as String
                }
                developers {
                    developer {
                        id = project.property("POM_DEVELOPER_ID") as String
                        name = project.property("POM_DEVELOPER_NAME") as String
                        email = project.property("POM_DEVELOPER_EMAIL") as String
                    }
                }
            }
        }
    }

    repositories {
        maven {
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = properties["ossrhUsername"] as String
                password = properties["ossrhPassword"] as String
            }
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
}