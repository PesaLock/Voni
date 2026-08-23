package co.tz.pesalock.voni.core.actions

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon

fun Modifier.onClick(enabled: Boolean = true, callback: () -> Unit) = pointerHoverIcon(PointerIcon.Hand).clickable(
    interactionSource = NoRippleInteractionSource,
    indication = null,
    enabled = enabled,
    onClick = callback
)
