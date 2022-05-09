plugins {
  kotlin("multiplatform")
  id("org.jetbrains.dokka")
  id("com.diffplug.spotless")
  id("org.jetbrains.kotlinx.binary-compatibility-validator")
  id("com.vanniktech.maven.publish")
}

val KTFMT_VERSION = "0.37"

spotless {
  kotlin {
    ktfmt(KTFMT_VERSION).googleStyle()
    target("**/*.kt")
    targetExclude("**/build/")
  }
  kotlinGradle {
    ktfmt(KTFMT_VERSION).googleStyle()
    target("**/*.kts")
    targetExclude("**/build/")
  }
}

kotlin {
  explicitApi()
  jvm()
  js(BOTH) {
    nodejs {}
    browser {}
    compilations.all {
      kotlinOptions {
        moduleKind = "umd"
        sourceMap = true
        sourceMapEmbedSources = null
      }
    }
  }
  iosX64()
  iosArm32()
  iosArm64()
  tvosX64()
  tvosArm64()
  watchosX86()
  watchosX64()
  watchosArm32()
  watchosArm64()
  macosX64()
  mingwX86()
  mingwX64()
  linuxX64()
  macosArm64()
  iosSimulatorArm64()
  watchosSimulatorArm64()
  tvosSimulatorArm64()

  sourceSets {
    val commonTest by getting {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-test")
        implementation("org.jetbrains.kotlin:kotlin-test-junit")
      }
    }
  }
}

signing {
  val signingKey: String? by project
  val signingPassword: String? by project
  useInMemoryPgpKeys(signingKey, signingPassword)
}
