// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("maven-publish")
    kotlin("jvm") version "1.5.21"
    id("com.android.library") version "8.1.2" apply false
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = "com.github.No98K"
            artifactId = "MLogUtil"
            version = "1.2.0"
        }
    }
    repositories {
        maven {
            setUrl(uri("https://jitpack.io"))
            credentials {
                username = project.findProperty("jitpack.username") as String? ?: ""
                password = project.findProperty("jitpack.token") as String? ?: ""
            }
            url = (uri("https://github.com/No98K/MLogUtil"))
        }
    }
}