plugins {
  kotlin("multiplatform")
  id("org.jetbrains.dokka") version "1.5.0"
  id("com.vanniktech.maven.publish") version "0.17.0"
  id("com.ncorti.ktfmt.gradle") version "0.6.0"
}

repositories {
  mavenCentral()
}

configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
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

signing {
  useGpgCmd()
}

ktfmt {
  googleStyle()
  maxWidth.set(100)
}
