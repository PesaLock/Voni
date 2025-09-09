package co.tz.pesalock.voni.drawer

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
class DrawerState(
    initialIsOpen: Boolean,
    val direction: DrawerDirection,
    val size: Float
) {
    var isOpen by mutableStateOf(initialIsOpen)
        private set

    fun open() {
        isOpen = true
    }

    fun close() {
        isOpen = false
    }

    fun toggle() {
        isOpen = !isOpen
    }
}