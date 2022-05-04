rootProject.name = "sublime-fuzzy"

pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
  }
  plugins {
    kotlin("multiplatform") version "1.6.10"
    id("org.jetbrains.dokka") version "1.6.21"
    id("com.ncorti.ktfmt.gradle") version "0.7.0"
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.8.0"
    id("com.vanniktech.maven.publish") version "0.18.0"
  }
  resolutionStrategy {
    eachPlugin {
      when (requested.id.id) {
        "com.vanniktech.maven.publish" -> {
          useModule("com.vanniktech:gradle-maven-publish-plugin:0.18.0")
        }
      }
    }
  }
}

include("sample")

dependencyResolutionManagement {
  repositories {
    mavenCentral()
  }
}
