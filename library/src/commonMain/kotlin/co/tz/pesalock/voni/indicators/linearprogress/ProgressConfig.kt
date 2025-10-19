package co.tz.pesalock.voni.indicators.linearprogress

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class ProgressConfig(
    val backgroundColor: Color,
    val progressColor: Color,
    val achievedLabelColor: Color,
    val unachievedLabelColor: Color,
    val achievedMarkerColor: Color,
    val unachievedMarkerColor: Color,
    val labelTextStyle: TextStyle,
    val cornerRadius: Dp,
    val showMarkers: Boolean,
    val markerWidth: Dp
)