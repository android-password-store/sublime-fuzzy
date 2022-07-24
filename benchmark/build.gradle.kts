import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.kotlinx.benchmark")
  id("org.jetbrains.kotlin.plugin.allopen")
}

fun KotlinNativeTargetWithHostTests.configureTarget() = binaries {
  executable { entryPoint = "main" }
}

allOpen { annotation("org.openjdk.jmh.annotations.State") }

benchmark { targets { register("jvm") } }

kotlin {
  jvm()
  js(LEGACY) {
    browser()
    nodejs()
    binaries.executable()
    compilations.all {
      kotlinOptions {
        moduleKind = "umd"
        sourceMap = true
        sourceMapEmbedSources = null
      }
    }
  }
  linuxX64 { configureTarget() }
  mingwX64 { configureTarget() }
  macosX64 { configureTarget() }

  sourceSets {
    sourceSets["commonMain"].apply {
      dependencies {
        implementation(project(":"))
        implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.4")
      }
    }
  }
}
