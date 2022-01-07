rootProject.name = "sublime-fuzzy"

pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    includeBuild("convention-plugins")
  }
  plugins {
    kotlin("multiplatform") version "1.6.10"
    id("org.jetbrains.dokka") version "1.6.10"
    id("com.ncorti.ktfmt.gradle") version "0.7.0"
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.8.0"
    id("convention.publication")
  }
}

dependencyResolutionManagement {
  repositories {
    mavenCentral()
  }
}
