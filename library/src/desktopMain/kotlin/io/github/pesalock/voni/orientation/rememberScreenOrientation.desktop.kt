package io.github.pesalock.voni.orientation

// Desktop implementation for screen orientation detection

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

            orientation.value = if (widthPx > heightPx) {
                Landscape
            } else {
                Portrait
            }

            layout(0, 0) {}
        }
    )

    return orientation.value
}