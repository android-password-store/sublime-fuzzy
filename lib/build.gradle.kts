plugins {
  kotlin("multiplatform")
  id("org.jetbrains.dokka") version "1.5.31"
  id("com.ncorti.ktfmt.gradle") version "0.7.0"
  id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.8.0-RC"
  id("convention.publication")
}

repositories {
  mavenCentral()
}

configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
  explicitApi()
  jvm()
  js(BOTH) {
    browser()
    nodejs()
  }
  ios()
  linuxX64()
  mingwX64()
  macosX64()
}

kotlin {
  sourceSets {
    val commonMain by getting
    val commonTest by getting {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-test")
        implementation("org.jetbrains.kotlin:kotlin-test-junit")
      }
    }
  }
}

ktfmt {
  googleStyle()
  maxWidth.set(100)
}
