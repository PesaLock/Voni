package co.tz.pesalock.voni.orientation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.Layout

@Composable
actual fun rememberScreenOrientation(): ScreenOrientation {
    val orientation = remember { mutableStateOf<ScreenOrientation>(Portrait) }

    Layout(
        content = {},
        measurePolicy = { _, constraints ->
            val widthPx = constraints.maxWidth
            val heightPx = constraints.maxHeight

            // If constraints are not meaningful (like -1 or 0), fallback to browser APIs
            if (widthPx <= 0 || heightPx <= 0) {
                val width = js("window.innerWidth") as Int
                val height = js("window.innerHeight") as Int
                orientation.value = if (width > height) Landscape else Portrait
            } else {
                orientation.value = if (widthPx > heightPx) Landscape else Portrait
            }

            layout(0, 0) {}
        }
    )

    return orientation.value
}
