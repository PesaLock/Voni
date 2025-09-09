package co.tz.pesalock.voni.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

/**
 * This is the ACTUAL implementation for the iOS platform.
 * Because Compose Multiplatform is awesome, the implementation is
 * identical to the Android one.
 */
@Composable
internal actual fun isSystemInDarkTheme(): Boolean = isSystemInDarkTheme()