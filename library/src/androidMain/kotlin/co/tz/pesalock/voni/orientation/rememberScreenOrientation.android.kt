package co.tz.pesalock.voni.orientation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

@SuppressLint("ConfigurationScreenWidthHeight")
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