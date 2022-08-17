import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.kotlinx.benchmark")
  id("org.jetbrains.kotlin.plugin.allopen")
}

fun KotlinNativeTargetWithHostTests.configureTarget() = binaries {
  executable { entryPoint = "main" }
}

allOpen { annotation("org.openjdk.jmh.annotations.State") }

benchmark {
  targets {
    register("js")
    register("jsIR")
    register("jvm")
    register("native")
  }
}

kotlin {
  jvm()
  js { nodejs() }
  js("jsIR", IR) { nodejs() }
  if (HostManager.hostIsLinux) linuxX64("native") { configureTarget() }
  if (HostManager.hostIsMingw) mingwX64("native") { configureTarget() }
  if (HostManager.hostIsMac) macosX64("native") { configureTarget() }

  sourceSets["commonMain"].dependencies {
    implementation(project(":"))
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.5")
  }
}
