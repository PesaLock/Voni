# Voni

[![Maven Central](https://img.shields.io/maven-central/v/tz.co.pesalock/voni.svg)](https://central.sonatype.com/artifact/tz.co.pesalock/voni)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg?logo=kotlin)](http://kotlinlang.org)

Voni is a versatile Kotlin Multiplatform library designed to simplify your UI development process. It provides a collection of essential UI components, including powerful charting tools and helpers for managing screen orientation across Android, iOS, Desktop (JVM), and Web (Wasm).

## Features

- **🎨 UI Components:** A set of ready-to-use, customizable UI components for Compose Multiplatform
- **📊 Charting:** Powerful and easy-to-use charting components to visualize your data
- **📱 Orientation Helpers:** Utilities to easily manage and respond to screen orientation changes
- **🌐 Kotlin Multiplatform:** Written in pure Kotlin, works seamlessly across all platforms

## Supported Platforms

- ✅ Android
- ✅ iOS (iosX64, iosArm64, iosSimulatorArm64)
- ✅ Desktop (JVM)
- ✅ Web (Wasm-JS)

## Installation

### Gradle (Kotlin DSL)

Add the dependency to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("tz.co.pesalock:voni:1.0.1")
}
```

### Version Catalog

Add to your `libs.versions.toml`:

```toml
[versions]
voni = "1.0.1"

[libraries]
voni = { module = "tz.co.pesalock:voni", version.ref = "voni" }
```

Then use in your `build.gradle.kts`:

```kotlin
dependencies {
    implementation(libs.voni)
}
```

### Platform-Specific Setup

For **Android**, ensure you have the minimum SDK version:

```kotlin
android {
    defaultConfig {
        minSdk = 24 // or higher
    }
}
```

For **iOS**, no additional setup is required.

For **Desktop (JVM)**, ensure you have Java 11 or higher.

For **Web (Wasm)**, enable the experimental Wasm target in your `build.gradle.kts`:

```kotlin
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
}
```

## Quick Start

Here's a simple example to get you started:

```kotlin
import androidx.compose.runtime.Composable
import tz.co.pesalock.voni.*

@Composable
fun App() {
    // Your Voni components here
    // Example: Charts, orientation-aware layouts, etc.
}
```

## Development

### Local Development (Composite Build)

For local development or when contributing to the library, you can include it as a composite build.

#### Option 1: Using Git Submodule (Recommended)

1. **Add the submodule:**
   ```bash
   git submodule add https://github.com/Pesalock/Voni.git voni
   ```

2. **Include the build in `settings.gradle.kts`:**
   ```kotlin
   includeBuild("voni")
   ```

3. **Add the dependency:**
   ```kotlin
   implementation("tz.co.pesalock:voni:1.0.1")
   ```

   Gradle will automatically use your local version instead of the remote one.

#### Option 2: Monorepo Setup

If Voni is in your project's repository structure:

```kotlin
// settings.gradle.kts
includeBuild("libs/voni") // adjust path as needed
```

### Building from Source

```bash
git clone https://github.com/Pesalock/Voni.git
cd Voni
./gradlew build
```

## Documentation

Detailed documentation and examples are coming soon. For now, explore the source code and check the examples in the repository.

## Roadmap

- [ ] Comprehensive documentation with examples
- [ ] Additional chart types
- [ ] More UI components
- [ ] Sample applications for each platform

## Contributing

Contributions are welcome! Here's how you can help:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

Please make sure to update tests as appropriate and follow the existing code style.

## Support

- **Issues:** [GitHub Issues](https://github.com/Pesalock/Voni/issues)
- **Email:** software@pesalock.co.tz
- **Website:** [pesalock.co.tz](https://pesalock.co.tz)

## License

Voni is licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt).

```
Copyright 2025 Pesalock Africa Limited

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Acknowledgments

Built with ❤️ by [Pesalock Africa Limited](https://pesalock.co.tz)

---

**Note:** This library is in early development (version 1.0.1). APIs may change in future releases.