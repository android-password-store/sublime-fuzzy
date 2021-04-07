plugins {
  id("org.jetbrains.kotlin.jvm") version "1.4.32"
  id("org.jetbrains.dokka") version "1.4.30"
  id("com.vanniktech.maven.publish") version "0.13.0"
  id("com.ncorti.ktfmt.gradle") version "0.5.0"
}

repositories {
  mavenCentral()
  maven("https://dl.bintray.com/kotlin/kotlinx") {
    name = "KotlinX Bintray"
    content {
      includeModule("org.jetbrains.kotlinx", "kotlinx-html-jvm")
    }
  }
}

dependencies {
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  testImplementation("org.jetbrains.kotlin:kotlin-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

signing {
  useGpgCmd()
}

ktfmt {
  googleStyle()
  maxWidth.set(100)
}
