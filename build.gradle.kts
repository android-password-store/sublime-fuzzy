plugins {
  kotlin("multiplatform")
  id("org.jetbrains.dokka")
  id("com.ncorti.ktfmt.gradle")
  id("org.jetbrains.kotlinx.binary-compatibility-validator")
  id("com.vanniktech.maven.publish")
}

configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
  explicitApi()
  jvm()
  js(BOTH) {
    browser()
    nodejs()
    compilations.all {
      kotlinOptions {
        moduleKind = "umd"
        sourceMap = true
        sourceMapEmbedSources = null
      }
    }
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

signing {
  val signingKey: String? by project
  val signingPassword: String? by project
  useInMemoryPgpKeys(signingKey, signingPassword)
}
