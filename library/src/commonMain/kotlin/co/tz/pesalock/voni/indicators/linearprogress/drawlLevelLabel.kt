package co.tz.pesalock.voni.indicators.linearprogress

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText

internal fun DrawScope.drawLevelLabel(
    textMeasurer: TextMeasurer,
    level: ProgressLevel,
    position: Float,
    progressHeight: Float,
    config: ProgressConfig,
    isAchieved: Boolean
) {
    val label = level.label
    val textColor = if (isAchieved) config.achievedLabelColor else config.unachievedLabelColor

    val textLayoutResult = textMeasurer.measure(
        text = label,
        style = config.labelTextStyle.copy(color = textColor)
    )

    val textWidth = textLayoutResult.size.width
    val textHeight = textLayoutResult.size.height

    // Position text in the center of the progress bar, aligned with the level position
    val textX = (position - textWidth / 2f).coerceIn(0f, size.width - textWidth)
    val textY = (progressHeight - textHeight) / 2f

    // Draw marker line if enabled
    if (config.showMarkers) {
        val markerHeight = progressHeight * 0.3f
        drawLine(
            color = if (isAchieved) config.achievedMarkerColor else config.unachievedMarkerColor,
            start = Offset(position, 0f),
            end = Offset(position, markerHeight),
            strokeWidth = config.markerWidth.toPx()
        )
    }

    // Draw text
    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = Offset(textX, textY)
    )
}