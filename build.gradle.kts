import com.vanniktech.maven.publish.KotlinMultiplatform
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.withType

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.dokka")
  id("com.diffplug.spotless")
  id("org.jetbrains.kotlinx.binary-compatibility-validator")
  id("com.vanniktech.maven.publish.base")
  id("signing")
}

group = requireNotNull(project.findProperty("GROUP"))

version = requireNotNull(project.findProperty("VERSION_NAME"))

@Suppress("UnstableApiUsage")
mavenPublishing {
  signAllPublications()
  pomFromGradleProperties()
  configure(KotlinMultiplatform())
}

publishing {
  repositories {
    maven {
      name = "Sonatype"
      setUrl {
        val repositoryId =
          System.getenv("SONATYPE_REPOSITORY_ID")
            ?: error("Missing env variable: SONATYPE_REPOSITORY_ID")
        "https://oss.sonatype.org/service/local/staging/deployByRepositoryId/${repositoryId}/"
      }
      credentials {
        username = System.getenv("SONATYPE_USERNAME")
        password = System.getenv("SONATYPE_PASSWORD")
      }
    }
  }
}

spotless {
  val ktfmtVersion = "0.37"
  kotlin {
    ktfmt(ktfmtVersion).googleStyle()
    target("**/*.kt")
    targetExclude("**/build/")
  }
  kotlinGradle {
    ktfmt(ktfmtVersion).googleStyle()
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
  androidNativeArm32()
  androidNativeArm64()

  sourceSets {
    getByName("commonTest") {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-test")
        implementation("org.jetbrains.kotlin:kotlin-test-junit")
      }
    }
  }
}

tasks.withType<Test>().configureEach {
  testLogging { events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED) }
  doNotTrackState("Prevent caching of tests so they are always executed")
}

signing {
  val signingKey: String? by project
  val signingPassword: String? by project
  useInMemoryPgpKeys(signingKey, signingPassword)
}
