package co.tz.pesalock.voni.charts.lines

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

data class LineSeries(
    val label: String,
    val values: List<Float>,
    val color: Color,
    val strokeWidth: Dp = 2.dp,
    val filled: Boolean = false
)

data class LineChartColors(
    val background: Color = Color.Transparent,
    val grid: Color = Color(0xFF2A2A2A),
    val axis: Color = Color(0xFF666666),
    val label: Color = Color(0xFF888888)
)

data class LineChartConfig(
    val series: List<LineSeries>,
    val xLabels: List<String> = emptyList(),
    val yMin: Float = 0f,
    val yMax: Float? = null,
    val gridLines: Int = 4,
    val colors: LineChartColors = LineChartColors(),
    val animationDurationMs: Int = 1000,
    val paddingStart: Dp = 40.dp,
    val paddingBottom: Dp = 28.dp,
    val paddingTop: Dp = 8.dp,
    val paddingEnd: Dp = 8.dp
)

@Composable
fun LineChart(
    config: LineChartConfig,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val animProgress = remember { Animatable(0f) }

    LaunchedEffect(config.series) {
        animProgress.snapTo(0f)
        animProgress.animateTo(1f, tween(config.animationDurationMs, easing = EaseInOutCubic))
    }

    val resolvedYMax = remember(config) {
        config.yMax ?: (config.series.flatMap { it.values }.maxOrNull()?.let { it * 1.1f } ?: 100f)
    }

    Canvas(modifier = modifier) {
        val pl = with(density) { config.paddingStart.toPx() }
        val pb = with(density) { config.paddingBottom.toPx() }
        val pt = with(density) { config.paddingTop.toPx() }
        val pr = with(density) { config.paddingEnd.toPx() }
        val chartW = size.width - pl - pr
        val chartH = size.height - pt - pb
        if (chartW <= 0 || chartH <= 0) return@Canvas

        val yRange = resolvedYMax - config.yMin

        // Grid lines + Y labels
        val steps = config.gridLines
        repeat(steps + 1) { i ->
            val frac = i.toFloat() / steps
            val yVal = config.yMin + yRange * frac
            val y = pt + chartH * (1f - frac)
            drawLine(
                color = config.colors.grid,
                start = Offset(pl, y),
                end = Offset(pl + chartW, y),
                strokeWidth = 1f
            )
            val labelStr = if (yVal >= 1000f) "${(yVal / 1000).roundToInt()}k" else "${yVal.roundToInt()}"
            val measured = textMeasurer.measure(labelStr, TextStyle(color = config.colors.label, fontSize = 9.sp))
            drawText(measured, topLeft = Offset(0f, y - measured.size.height / 2f))
        }

        // X labels
        val xCount = config.xLabels.size
        if (xCount > 0) {
            val maxSeriesLen = config.series.maxOfOrNull { it.values.size } ?: xCount
            config.xLabels.forEachIndexed { i, label ->
                val xFrac = if (maxSeriesLen > 1) i.toFloat() / (maxSeriesLen - 1) else 0.5f
                val x = pl + chartW * xFrac
                val measured = textMeasurer.measure(label, TextStyle(color = config.colors.label, fontSize = 8.sp))
                drawText(measured, topLeft = Offset(x - measured.size.width / 2f, pt + chartH + 4.dp.toPx()))
            }
        }

        // Axes
        drawLine(config.colors.axis, Offset(pl, pt), Offset(pl, pt + chartH), strokeWidth = 1f)
        drawLine(config.colors.axis, Offset(pl, pt + chartH), Offset(pl + chartW, pt + chartH), strokeWidth = 1f)

        // Series lines using cubic bezier
        config.series.forEach { series ->
            val n = series.values.size
            if (n < 2) return@forEach

            fun xAt(i: Int) = pl + chartW * (i.toFloat() / (n - 1))
            fun yAt(v: Float): Float {
                val normalised = ((v - config.yMin) / yRange).coerceIn(0f, 1f)
                return pt + chartH * (1f - normalised)
            }

            val path = Path()
            val drawLen = (n * animProgress.value).coerceAtLeast(1f)

            path.moveTo(xAt(0), yAt(series.values[0]))
            for (i in 1 until n) {
                if (i > drawLen) break
                val x0 = xAt(i - 1); val y0 = yAt(series.values[i - 1])
                val x1 = xAt(i);     val y1 = yAt(series.values[i])
                val cpX = (x0 + x1) / 2f
                path.cubicTo(cpX, y0, cpX, y1, x1, y1)
            }

            val sw = with(density) { series.strokeWidth.toPx() }
            drawPath(path, series.color, style = Stroke(width = sw, cap = StrokeCap.Round, join = StrokeJoin.Round))

            if (series.filled) {
                val fillPath = Path().apply {
                    addPath(path)
                    lineTo(xAt((drawLen - 1).toInt().coerceIn(0, n - 1)), pt + chartH)
                    lineTo(xAt(0), pt + chartH)
                    close()
                }
                drawPath(fillPath, Brush.verticalGradient(listOf(series.color.copy(alpha = 0.25f), Color.Transparent)))
            }
        }
    }
}
