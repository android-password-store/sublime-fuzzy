rootProject.name = "sublime-fuzzy"

pluginManagement {
  plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0" }
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
}

include("benchmark")

include("sample")

dependencyResolutionManagement { repositories { mavenCentral() } }
