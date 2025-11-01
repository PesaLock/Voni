package co.tz.pesalock.voni.orientation

import androidx.compose.runtime.Composable

/**
 * Remembers the current screen orientation state.
 *
 * @return ScreenOrientation.Portrait or ScreenOrientation.Landscape
 */
@Composable
expect fun rememberScreenOrientation(): ScreenOrientation