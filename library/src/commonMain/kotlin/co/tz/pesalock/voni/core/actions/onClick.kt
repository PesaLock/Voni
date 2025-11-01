package co.tz.pesalock.voni.core.actions

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier

fun Modifier.onClick(callback: () -> Unit) = clickable(
    interactionSource = NoRippleInteractionSource,
    indication = null,
    enabled = true,
    onClick = callback
)