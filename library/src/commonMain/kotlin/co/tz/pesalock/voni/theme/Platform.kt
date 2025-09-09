package co.tz.pesalock.voni.theme

import androidx.compose.runtime.Composable

/**
 * This is the contract. It declares that every platform MUST provide
 * a composable function that can tell us if the system is in dark mode.
 * The rest of the common code will call this function without knowing
 * how each platform actually implements it.
 */
@Composable
internal expect fun isSystemInDarkTheme(): Boolean