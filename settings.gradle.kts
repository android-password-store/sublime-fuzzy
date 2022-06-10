rootProject.name = "sublime-fuzzy"

pluginManagement {
  repositories {
    exclusiveContent {
      forRepository(::gradlePluginPortal)
      filter {
        listOf("com.diffplug.spotless", "org.jetbrains.kotlin.multiplatform").forEach { plugin ->
          includeModule(plugin, "${plugin}.gradle.plugin")
        }
      }
    }
    mavenCentral()
  }
  plugins {
    kotlin("multiplatform") version "1.7.0"
    id("org.jetbrains.dokka") version "1.6.21"
    id("com.diffplug.spotless") version "6.7.1"
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.10.0"
    id("com.vanniktech.maven.publish.base") version "0.20.0"
  }
}

include("sample")

dependencyResolutionManagement { repositories { mavenCentral() } }
