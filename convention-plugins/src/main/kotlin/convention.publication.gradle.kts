import java.util.*
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`
import org.gradle.kotlin.dsl.signing

plugins {
  `maven-publish`
  signing
}

val javadocJar by tasks.registering(Jar::class) { archiveClassifier.set("javadoc") }

fun getProperty(name: String): String {
  return project.findProperty(name).toString()
}

publishing {
  // Configure maven central repository
  repositories {
    maven {
      name = "sonatype"
      setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
      credentials {
        username = getProperty("mavenCentralUsername")
        password = getProperty("mavenCentralPassword")
      }
    }
  }

  // Configure all publications
  publications.withType<MavenPublication> {

    // Stub javadoc.jar artifact
    artifact(javadocJar.get())

    groupId = getProperty("GROUP")
    artifactId = getProperty("POM_ARTIFACT_ID")
    version = getProperty("VERSION_NAME")

    // Provide artifacts information requited by Maven Central
    pom {
      name.set(getProperty("POM_ARTIFACT_ID"))
      description.set(getProperty("POM_DESCRIPTION"))
      url.set(getProperty("POM_URL"))

      licenses {
        license {
          name.set(getProperty("POM_LICENCE_NAME"))
          url.set(getProperty("POM_LICENCE_URL"))
        }
      }

      developers {
        developer {
          id.set(getProperty("POM_DEVELOPER_ID"))
          name.set(getProperty("POM_DEVELOPER_NAME"))
          email.set(getProperty("POM_DEVELOPER_EMAIL"))
        }
      }
      scm {
        connection.set(getProperty("POM_SCM_CONNECTION"))
        developerConnection.set(getProperty("POM_SCM_DEV_CONNECTION"))
        url.set(getProperty("POM_SCM_URL"))
      }
    }
  }
}

// Signing artifacts. Signing.* extra properties values will be used

signing {
  val signingKey: String? by project
  val signingPassword: String? by project
  useInMemoryPgpKeys(signingKey, signingPassword)
  sign(publishing.publications)
}
