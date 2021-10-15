plugins {
  `kotlin-dsl`
  id("com.ncorti.ktfmt.gradle") version "0.7.0"
}

repositories {
  gradlePluginPortal()
}

ktfmt {
  googleStyle()
  maxWidth.set(100)
}
