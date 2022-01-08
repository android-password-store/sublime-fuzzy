# sublime-fuzzy [![Maven Central](https://img.shields.io/maven-central/v/com.github.android-password-store/sublime-fuzzy.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.android-password-store%22%20AND%20a:%22sublime-fuzzy%22)

sublime-fuzzy is a pure Kotlin implementation of [Sublime Text]'s fuzzy searching algorithm, widely recognized to be among the best in the industry.

It is available as a Kotlin Multiplatform library for the following platforms

- Android
- JVM
- NodeJS
- Browsers (JavaScript)
- Linux x64
- MingW x64
- MingW x86
- iOS arm32
- iOS arm64
- iOS x64
- tvOS arm64
- tvOS x64
- watchOS arm32
- watchOS arm64
- macOS x64
- iOS simulator arm64
- tvOS simulator arm64
- watchOS simulator arm64

## Downloading

For single-platform projects

```kotlin
dependencies {
  implementation("com.github.android-password-store:sublime-fuzzy:<latest version>")
}
```

For Kotlin multiplatform projects

```kotlin
kotlin {
  sourceSets["commonMain"].dependencies {
    implementation("com.github.android-password-store:sublime-fuzzy:<latest version>")
  }
}
```

## Acknowledgements

Huge thanks to Forrest Smith for their blog '[Reverse Engineering Sublime Text's Fuzzy Match]' that served as a baseline for this implementation.

[Sublime Text]: https://www.sublimetext.com/
[Reverse Engineering Sublime Text's Fuzzy Match]: https://www.forrestthewoods.com/blog/reverse_engineering_sublime_texts_fuzzy_match/
