package co.tz.pesalock.voni.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

/**
 * This is the ACTUAL implementation for the Android platform.
 * It fulfills the `expect` contract from commonMain by calling
 * the standard `isSystemInDarkTheme` function from the Compose library.
 */
@Composable
internal actual fun isSystemInDarkTheme(): Boolean = isSystemInDarkTheme()