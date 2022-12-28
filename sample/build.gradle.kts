import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
  application
  kotlin("multiplatform")
}

fun KotlinNativeTargetWithHostTests.configureTarget() = binaries {
  executable { entryPoint = "main" }
}

kotlin {
  js(IR) {
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
  if (providers.gradleProperty("enableNativeTargets").isPresent) {
    if (HostManager.hostIsLinux) linuxX64 { configureTarget() }
    if (HostManager.hostIsMingw) mingwX64 { configureTarget() }
    if (HostManager.hostIsMac) macosX64 { configureTarget() }
  }

  sourceSets { sourceSets["commonMain"].apply { dependencies { implementation(project(":")) } } }
}
