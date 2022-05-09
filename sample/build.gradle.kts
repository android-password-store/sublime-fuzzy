import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests

plugins {
  application
  kotlin("multiplatform")
}

fun KotlinNativeTargetWithHostTests.configureTarget() = binaries {
  executable { entryPoint = "main" }
}

kotlin {
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
  ios()
  linuxX64 { configureTarget() }
  mingwX64 { configureTarget() }
  macosX64 { configureTarget() }

  sourceSets { sourceSets["commonMain"].apply { dependencies { implementation(project(":")) } } }
}
