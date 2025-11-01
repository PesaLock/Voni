package co.tz.pesalock.voni.indicators.linearprogress

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object ProgressDefaults {
    @Composable
    fun defaultConfig(
        backgroundColor: Color = Color(0xFFE0E0E0),
        progressColor: Color = Color(0xFF4CAF50),
        achievedLabelColor: Color = Color.White,
        unachievedLabelColor: Color = Color.DarkGray,
        achievedMarkerColor: Color = Color.White.copy(alpha = 0.7f),
        unachievedMarkerColor: Color = Color.Gray,
        labelTextStyle: TextStyle = TextStyle(fontSize = 12.sp),
        cornerRadius: Dp = 8.dp,
        showMarkers: Boolean = true,
        markerWidth: Dp = 2.dp
    ): ProgressConfig {
        return ProgressConfig(
            backgroundColor = backgroundColor,
            progressColor = progressColor,
            achievedLabelColor = achievedLabelColor,
            unachievedLabelColor = unachievedLabelColor,
            achievedMarkerColor = achievedMarkerColor,
            unachievedMarkerColor = unachievedMarkerColor,
            labelTextStyle = labelTextStyle,
            cornerRadius = cornerRadius,
            showMarkers = showMarkers,
            markerWidth = markerWidth
        )
    }
}