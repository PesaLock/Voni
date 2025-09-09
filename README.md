# Voni

Voni is a versatile Kotlin Multiplatform library designed to simplify your UI development process. It provides a collection of essential UI components, including powerful charting tools and helpers for managing screen orientation.

## Features

*   **UI Components:** A set of ready-to-use, customizable UI components for Compose Multiplatform.
*   **Charting:** Powerful and easy-to-use charting components to visualize your data.
*   **Orientation Helpers:** Utilities to easily manage and respond to screen orientation changes.
*   **Kotlin Multiplatform:** Written in pure Kotlin, `Voni` works seamlessly across Android, iOS, Desktop, and Web.

## Usage

You can integrate `Voni` into your project in two ways: for local development using a composite build, or as a remote dependency from Maven Central.

### Local Development (Composite Build)

For local development or when you need to make changes to the library, you can include it as a composite build.

#### Option 1: Using Git Submodule (Recommended)

This approach uses a Git submodule to keep the library's repository completely separate but linked to your main project. A submodule is a separate Git repository nested inside your main project. The main project only stores a reference to a specific commit in the submodule's history, ensuring the projects remain decoupled while allowing for direct, version-controlled integration.

**Workflow:**

1.  **Add the Submodule:**
    From the root of your main project, run the following command. This will clone the `Voni` repository into a `voni` directory.

    ```bash
    git submodule add <URL_to_Voni_repository> voni
    ```

2.  **Develop in the Submodule:**
    Navigate into the `voni` directory (`cd voni`). You are now working inside the `Voni` repository. You can make changes, create branches, and commit as you would in any normal repository. These commits **only affect Voni's history**.

3.  **Update the Parent Project:**
    After committing your changes in the submodule, navigate back to your main project's root (`cd ..`). Running `git status` will show that the `voni` directory has been modified. Staging and committing this change simply updates the pointer in your main project to the new commit in `Voni`.

4.  **Include the Build in Gradle:**
    In your project's `settings.gradle.kts` file, include the `Voni` build, pointing to the submodule directory:

    ```kotlin
    includeBuild("voni")
    ```

5.  **Add the Dependency:**
    In your module's `build.gradle.kts` file, add the dependency using the library's coordinates. Gradle's dependency substitution will automatically replace this with a local project dependency.

    ```kotlin
    implementation("co.tz.pesalock:voni:1.0.0-alpha01")
    ```

    Or, if you have it defined in your version catalog:

    ```kotlin
    implementation(libs.voni)
    ```

#### Option 2: Monorepo Setup

If `Voni` is located in a directory within your main project's repository (a monorepo setup), you can point `includeBuild` to its location.

1.  **Include the Build:**
    In your project's `settings.gradle.kts` file, include the `Voni` build:

    ```kotlin
    // e.g., if Voni is in a 'libs/voni' directory
    includeBuild("libs/voni")
    ```

2.  **Add the Dependency:**
    The dependency setup is the same as with the submodule approach.

### Maven Central

Once `Voni` is published, you can add it as a remote dependency from Maven Central.

1.  **Add the Repository:**
    Ensure you have `mavenCentral()` in your project's repository list in `settings.gradle.kts`:

    ```kotlin
    dependencyResolutionManagement {
        repositories {
            mavenCentral()
        }
    }
    ```

2.  **Add the Dependency:**
    In your module's `build.gradle.kts` file, add the dependency:

    ```kotlin
    implementation("co.tz.pesalock:voni:1.0.0-alpha01")
    ```

## Contributing

Contributions are welcome! If you have any ideas, suggestions, or bug reports, please open an issue or submit a pull request.

## License

Voni is licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt).
