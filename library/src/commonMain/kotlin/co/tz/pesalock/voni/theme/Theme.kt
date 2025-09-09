package co.tz.pesalock.voni.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * The default color palette used for the Light theme in the Voni library.
 */
val VoniDefaultLightColors = ThemeColors(
    primary = ColorTriplet(
        companion = LightColors.heavyBlue,
        dominant = LightColors.heavyWhite,
        support = LightColors.heavyGreen
    ),
    secondary = ColorTriplet(
        companion = LightColors.lighterBlue,
        dominant = LightColors.lighterWhite,
        support = LightColors.lighterGreen
    )
)

/**
 * The default color palette used for the Dark theme in the Voni library.
 */
val VoniDefaultDarkColors = ThemeColors(
    primary = ColorTriplet(
        companion = DarkColors.heavyWhite,
        dominant = DarkColors.heavyDark,
        support = DarkColors.heavyGreen
    ),
    secondary = ColorTriplet(
        companion = DarkColors.lighterWhite,
        dominant = DarkColors.lighterDark,
        support = DarkColors.lighterGreen
    )
)

/**
 * A powerful, self-contained theme engine for Compose Multiplatform.
 *
 * This engine manages the user's theme preference (System, Light, or Dark) and allows
 * the developer to override the default color palettes for a fully customized look and feel.
 *
 * @param initialTheme The user's starting theme preference. Defaults to [System].
 * @param lightColors The color palette to be used for the [Light] theme. Defaults to [VoniDefaultLightColors].
 * @param darkColors The color palette to be used for the [Dark] theme. Defaults to [VoniDefaultDarkColors].
 */
class ThemeEngine(
    initialTheme: AppTheme = System,
    private val lightColors: ThemeColors = VoniDefaultLightColors,
    private val darkColors: ThemeColors = VoniDefaultDarkColors
) {
    // The user's preference (System, Light, or Dark). This is private and managed via setMode.
    var theme by mutableStateOf(initialTheme)
        private set

    // The actual theme being displayed (either Light or Dark).
    // This is resolved by the rememberThemeEngine composable.
    var activeTheme by mutableStateOf<AppTheme>(Light)
        internal set

    /**
     * The currently active color palette, resolved based on the user's preference and the system theme.
     *
     * This property relies on the `rememberThemeEngine` composable to correctly update the `activeTheme`.
     * The `activeTheme` should only ever be [Light] or [Dark], as the [System] option is resolved
     * into one of the other two by the composable.
     */
    val colors: ThemeColors
        get() = when (activeTheme) {
            Light -> lightColors
            Dark -> darkColors
            // This branch should be unreachable when using the engine via `rememberThemeEngine`.
            // If it is reached, it indicates an improper usage of the ThemeEngine.
            System -> error("activeTheme cannot be System. The theme must be resolved by the rememberThemeEngine composable.")
        }

    /**
     * Sets the user's theme preference. The UI will automatically update to reflect
     * this new preference.
     * @param mode The new theme preference to set. Can be [System], [Light], or [Dark].
     */
    fun setMode(mode: AppTheme) {
        this.theme = mode
    }
}

/**
 * Remembers and manages a [ThemeEngine] instance across recompositions.
 *
 * This is the primary entry point for using the Voni theme system. It creates a [ThemeEngine]
 * and intelligently resolves the currently active theme based on the user's preference
 * (`engine.theme`) and the actual system theme (`isSystemInDarkTheme()`).
 *
 * The returned [ThemeEngine] can be passed down through your UI hierarchy or provided
 * via a CompositionLocal.
 *
 * @param initialTheme The user's starting theme preference. Defaults to [System].
 * @param lightColors An optional parameter to provide a custom color palette for the [Light] theme.
 *                    If not provided, [VoniDefaultLightColors] will be used.
 * @param darkColors An optional parameter to provide a custom color palette for the [Dark] theme.
 *                   If not provided, [VoniDefaultDarkColors] will be used.
 * @return A remembered instance of [ThemeEngine].
 *
 * @sample
 * // Example of creating a theme engine with custom colors
 * 
 * // In your custom color definition file
 * val myCustomLightColors = ThemeColors(...)
 * val myCustomDarkColors = ThemeColors(...)
 * 
 * // In your main Composable
 * @Composable
 * fun MyApp() {
 *     val themeEngine = rememberThemeEngine(
 *         initialTheme = System,
 *         lightColors = myCustomLightColors,
 *         darkColors = myCustomDarkColors
 *     )
 *     // ... rest of your app UI, using themeEngine.colors ...
 * }
 */
@Composable
fun rememberThemeEngine(
    initialTheme: AppTheme = System,
    lightColors: ThemeColors = VoniDefaultLightColors,
    darkColors: ThemeColors = VoniDefaultDarkColors
): ThemeEngine {
    val engine = remember(initialTheme, lightColors, darkColors) {
        ThemeEngine(initialTheme, lightColors, darkColors)
    }

    val isSystemDark = isSystemInDarkTheme()

    // This is the core logic that resolves the final theme.
    val newActiveTheme = when (engine.theme) {
        System -> if (isSystemDark) Dark else Light
        Light -> Light
        Dark -> Dark
    }

    // Update the engine's active theme. This will trigger recomposition
    // for any composable that uses `engine.colors`.
    engine.activeTheme = newActiveTheme

    return engine
}