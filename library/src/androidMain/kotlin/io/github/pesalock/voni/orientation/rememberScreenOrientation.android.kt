package io.github.pesalock.voni.orientation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import co.tz.pesalock.voni.orientation.Landscape
import co.tz.pesalock.voni.orientation.Portrait
import co.tz.pesalock.voni.orientation.ScreenOrientation

@Composable
actual fun rememberScreenOrientation(): ScreenOrientation {
    val config = LocalConfiguration.current
    val widthDp = config.screenWidthDp
    val heightDp = config.screenHeightDp

    return remember(widthDp, heightDp) {
        if (widthDp > heightDp) {
            Landscape
        } else {
            Portrait
        }
    }
}
