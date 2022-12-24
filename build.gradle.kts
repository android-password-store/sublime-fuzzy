import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.dokka)
  alias(libs.plugins.spotless)
  alias(libs.plugins.kotlinxBCV)
  alias(libs.plugins.mavenPublish)
  id("signing")
}

group = requireNotNull(project.findProperty("GROUP"))

version = requireNotNull(project.findProperty("VERSION_NAME"))

apiValidation { ignoredProjects.add("benchmark") }

@Suppress("UnstableApiUsage")
mavenPublishing {
  signAllPublications()
  pomFromGradleProperties()
  configure(KotlinMultiplatform(JavadocJar.Dokka("dokkaHtml")))
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
  val ktfmtVersion = "0.42"
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
  if (providers.gradleProperty("enableNativeTargets").isPresent) {
    if (HostManager.hostIsMac) {
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
      macosArm64()
      iosSimulatorArm64()
      watchosSimulatorArm64()
      tvosSimulatorArm64()
    }
    if (HostManager.hostIsMingw) {
      mingwX86()
      mingwX64()
    }
    if (HostManager.hostIsLinux) {
      linuxX64()
    }
    androidNativeArm32()
    androidNativeArm64()
  }

  sourceSets {
    getByName("commonTest") {
      dependencies {
        implementation(libs.kotlin.test.core)
        implementation(libs.kotlin.test.junit)
      }
    }
  }
}

tasks.withType<Test>().configureEach {
  testLogging { events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED) }
}

signing {
  val signingKey: String? by project
  val signingPassword: String? by project
  useInMemoryPgpKeys(signingKey, signingPassword)
}
