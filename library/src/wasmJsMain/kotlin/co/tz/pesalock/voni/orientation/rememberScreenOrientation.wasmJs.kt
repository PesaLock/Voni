package co.tz.pesalock.voni.orientation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.browser.window
import org.w3c.dom.events.Event

@Composable
actual fun rememberScreenOrientation(): ScreenOrientation {
    var orientation by remember {
        mutableStateOf(
            if (window.innerWidth > window.innerHeight) Landscape else Portrait
        )
    }

    DisposableEffect(Unit) {
        val listener = { _: Event ->
            orientation = if (window.innerWidth > window.innerHeight) {
                Landscape
            } else {
                Portrait
            }
        }

        window.addEventListener("resize", listener)
        onDispose {
            window.removeEventListener("resize", listener)
        }
    }

    return orientation
}
