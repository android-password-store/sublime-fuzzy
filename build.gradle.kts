import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JsModuleKind
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.dokka)
  alias(libs.plugins.spotless)
  alias(libs.plugins.kotlinxBCV)
  alias(libs.plugins.mavenPublish)
}

group = requireNotNull(project.findProperty("GROUP"))

version = requireNotNull(project.findProperty("VERSION_NAME"))

apiValidation { ignoredProjects.add("benchmark") }

@Suppress("UnstableApiUsage")
mavenPublishing {
  pomFromGradleProperties()
  configure(KotlinMultiplatform(javadocJar = JavadocJar.Dokka("dokkaGenerate"), sourcesJar = true))
}

spotless {
  val ktfmtVersion = "0.58"
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
  js(IR) {
    nodejs {}
    browser {}
    compilerOptions { moduleKind.set(JsModuleKind.MODULE_UMD) }
  }
  if (providers.gradleProperty("enableNativeTargets").isPresent) {
    if (HostManager.hostIsMac) {
      iosX64()
      iosArm64()
      tvosX64()
      tvosArm64()
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
      mingwX64()
    }
    if (HostManager.hostIsLinux || HostManager.hostIsMac) {
      linuxX64()
    }
    androidNativeArm32()
    androidNativeArm64()
  }

  sourceSets { getByName("commonTest") { dependencies { implementation(libs.kotlin.test.core) } } }

  jvmToolchain { languageVersion.set(JavaLanguageVersion.of(11)) }
}

tasks.withType<Test>().configureEach {
  testLogging { events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED) }
}
