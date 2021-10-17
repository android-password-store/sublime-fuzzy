rootProject.name = "sublime-fuzzy"

pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    includeBuild("convention-plugins")
  }
  plugins {
    kotlin("multiplatform") version "1.5.31" apply false
    id("org.jetbrains.dokka") version "1.5.31" apply false
    id("com.ncorti.ktfmt.gradle") version "0.7.0" apply false
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.8.0-RC" apply false
    id("convention.publication") apply false
  }
}

dependencyResolutionManagement {
  repositories {
    mavenCentral()
  }
}
