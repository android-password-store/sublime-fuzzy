rootProject.name = "sublime-fuzzy"

pluginManagement {
  repositories {
    exclusiveContent {
      forRepository(::gradlePluginPortal)
      filter {
        includeModule("org.jetbrains.kotlinx", "kotlinx-benchmark-plugin")
        val plugins =
          listOf(
            "com.diffplug.spotless",
            "org.jetbrains.kotlin.multiplatform",
            "org.jetbrains.kotlin.plugin.allopen",
            "org.jetbrains.kotlinx.benchmark",
          )
        plugins.forEach { plugin -> includeModule(plugin, "${plugin}.gradle.plugin") }
      }
    }
    mavenCentral()
  }

  plugins {
    kotlin("multiplatform") version "1.7.10"
    id("org.jetbrains.dokka") version "1.7.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.7.10"
    id("com.diffplug.spotless") version "6.10.0"
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.11.1"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.5"
    id("com.vanniktech.maven.publish.base") version "0.21.0"
  }
}

include("benchmark")

include("sample")

dependencyResolutionManagement { repositories { mavenCentral() } }
