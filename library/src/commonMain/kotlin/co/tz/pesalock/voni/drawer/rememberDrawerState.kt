package co.tz.pesalock.voni.drawer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberDrawerState(
    isOpen: Boolean = false,
    direction: DrawerDirection = Left,
    size: Float = 500f
): DrawerState {
    return remember {
        DrawerState(isOpen, direction, size)
    }
}