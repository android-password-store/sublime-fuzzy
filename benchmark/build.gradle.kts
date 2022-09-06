import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.kotlinxBenchmark)
  alias(libs.plugins.allopen)
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
    implementation(libs.kotlinx.benchmark.runtime)
  }
}
