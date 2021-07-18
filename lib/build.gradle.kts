plugins {
  id("org.jetbrains.kotlin.jvm") version "1.5.21"
  id("org.jetbrains.dokka") version "1.5.0"
  id("com.vanniktech.maven.publish") version "0.17.0"
  id("com.ncorti.ktfmt.gradle") version "0.6.0"
}

repositories {
  mavenCentral()
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
