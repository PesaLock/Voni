package co.tz.pesalock.voni.dates

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
data class CalendarColors(
    val surface: Color,
    val onSurface: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val textDim: Color,
    val line: Color,
    val rangeBackground: Color
)

object InteractiveCalendarDefaults {
    @Composable
    fun colors(
        surface: Color = MaterialTheme.colorScheme.surface,
        onSurface: Color = MaterialTheme.colorScheme.onSurface,
        primary: Color = MaterialTheme.colorScheme.primary,
        onPrimary: Color = MaterialTheme.colorScheme.onPrimary,
        secondary: Color = MaterialTheme.colorScheme.secondary,
        textDim: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        line: Color = MaterialTheme.colorScheme.outlineVariant,
        rangeBackground: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
    ): CalendarColors = CalendarColors(
        surface = surface,
        onSurface = onSurface,
        primary = primary,
        onPrimary = onPrimary,
        secondary = secondary,
        textDim = textDim,
        line = line,
        rangeBackground = rangeBackground
    )
}
