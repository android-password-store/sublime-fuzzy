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
}

include("benchmark")

include("sample")

dependencyResolutionManagement { repositories { mavenCentral() } }
