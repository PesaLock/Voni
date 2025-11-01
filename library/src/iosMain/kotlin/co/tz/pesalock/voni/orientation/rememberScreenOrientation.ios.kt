package co.tz.pesalock.voni.orientation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalWindowInfo

@Composable
actual fun rememberScreenOrientation(): ScreenOrientation {
    val windowInfo = LocalWindowInfo.current
    val containerSize = windowInfo.containerSize

    return remember(containerSize.width, containerSize.height) {
        if (containerSize.width > containerSize.height) {
            Landscape
        } else {
            Portrait
        }
    }
}