package co.tz.pesalock.voni.indicators.linearprogress

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.rememberTextMeasurer

/**
 * A custom linear progress indicator with labels at each progress level
 *
 * @param progress Current progress value
 * @param total Total value representing 100% completion
 * @param levels List of progress levels to display labels at
 * @param modifier Modifier for the progress indicator
 * @param config Configuration for colors and styling
 */
@Composable
fun LabeledLinearProgress(
    progress: Float,
    total: Float,
    levels: List<ProgressLevel>,
    modifier: Modifier = Modifier,
    config: ProgressConfig = ProgressDefaults.defaultConfig()
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
    ) {
        val progressWidth = size.width
        val progressHeight = size.height
        val cornerRadius = config.cornerRadius.toPx()

        drawRoundRect(
            color = config.backgroundColor,
            topLeft = Offset.Zero,
            size = Size(progressWidth, progressHeight),
            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
        )

        val currentProgressWidth = (progress / total).coerceIn(0f, 1f) * progressWidth

        if (currentProgressWidth > 0f) {
            drawRoundRect(
                color = config.progressColor,
                topLeft = Offset.Zero,
                size = Size(currentProgressWidth, progressHeight),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
        }

        levels.forEach { level ->
            val levelPosition = (level.value / total).coerceIn(0f, 1f) * progressWidth

            drawLevelLabel(
                textMeasurer = textMeasurer,
                level = level,
                position = levelPosition,
                progressHeight = progressHeight,
                config = config,
                isAchieved = progress >= level.value
            )
        }
    }
}