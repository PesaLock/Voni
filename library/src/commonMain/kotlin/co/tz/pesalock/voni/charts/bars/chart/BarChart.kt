package co.tz.pesalock.voni.charts.bars.chart

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.tz.pesalock.voni.charts.bars.dataConversion
import co.tz.pesalock.voni.charts.bars.getFloatValue
import co.tz.pesalock.voni.charts.bars.setup.BarData
import co.tz.pesalock.voni.charts.bars.setup.ChartConfig
import co.tz.pesalock.voni.charts.bars.setup.ChartOrientation

private data class BarRenderInfo(val rect: Rect, val data: BarData)

@Composable
public fun BarChart(
    data: List<BarData>,
    modifier: Modifier = Modifier,
    config: ChartConfig
) {
    val processedData = dataConversion(data, config.resultType)
    var animationPlayed by remember { mutableStateOf(false) }
    val animationProgress by animateFloatAsState(
        targetValue = if (animationPlayed) 1f else 0f,
        animationSpec = tween(config.animationDuration, easing = EaseOutCubic),
        label = "bar_animation"
    )

    LaunchedEffect(Unit) {
        animationPlayed = true
    }

    val maxValue = remember(processedData) {
        processedData.maxOfOrNull { getFloatValue(it) } ?: 1f
    }

    BoxWithConstraints(modifier = modifier) {
        val density = LocalDensity.current
        val layoutDirection = LocalLayoutDirection.current

        val canvasWidth = with(density) { maxWidth.toPx() }
        val canvasHeight = with(density) { maxHeight.toPx() }

        val leftMargin: Float
        val rightMargin: Float
        val topMargin: Float
        val bottomMargin: Float

        with(density) {
            leftMargin = config.padding?.calculateLeftPadding(layoutDirection)?.toPx() ?: 80f
            rightMargin = config.padding?.calculateRightPadding(layoutDirection)?.toPx() ?: 40f
            topMargin = config.padding?.calculateTopPadding()?.toPx() ?: 40f
            bottomMargin = config.padding?.calculateBottomPadding()?.toPx() ?: 60f
        }

        val chartWidth = canvasWidth - leftMargin - rightMargin
        val chartHeight = canvasHeight - topMargin - bottomMargin

        val barRenderInfo = remember(processedData, chartWidth, chartHeight, animationProgress) {
            calculateBarPositions(
                data = processedData,
                config = config,
                chartWidth = chartWidth,
                chartHeight = chartHeight,
                leftMargin = leftMargin,
                topMargin = topMargin,
                maxValue = maxValue,
                animationProgress = animationProgress
            )
        }

        // 1. Draw Background (Grid and Axes)
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (chartWidth <= 0 || chartHeight <= 0) return@Canvas

            if (config.part.showGrid) {
                drawGrid(chartWidth, chartHeight, leftMargin, topMargin, config.colors.grid, config.orientation)
            }
            drawAxes(chartWidth, chartHeight, leftMargin, topMargin, config)
        }

        // 2. Draw Bars
        Canvas(modifier = Modifier.fillMaxSize()) {
            barRenderInfo.forEach { info ->
                drawBarShape(info.data.shape, info.data.color, info.rect.topLeft, info.rect.size)
            }
        }

        // 3. Draw Labels and Scale Values
        if (config.orientation == ChartOrientation.Vertical) {
            // Draw Y-Axis Scale
            if (config.part.showYAxis) {
                val steps = 5
                repeat(steps + 1) { i ->
                    val value = (maxValue / steps) * i
                    val y = topMargin + chartHeight - (chartHeight / steps) * i
                 Box(
                     modifier = Modifier
                         .border(1.dp, Color.Red)
                         .width(with(density) { leftMargin.toDp() } - 16.dp)
                         .offset(
                             x = 0.dp, // Push 4dp from the right
                             y = with(density) { (y - config.valueTextSize.sp.toPx() / 2).toDp() }
                         ),
                     contentAlignment = Alignment.CenterEnd
                 ) {
                     Text(
                         text = "${value.toInt()}",
                         textAlign = TextAlign.End,
                         fontSize = config.valueTextSize.sp,
                         color = config.colors.value
                     )
                 }
                }
            }
            // Draw X-Axis Labels and End Values
            barRenderInfo.forEach { info ->
                val barRect = info.rect
                with(density) {
                    if (config.part.showXAxis) {
                        Box(
                            modifier = Modifier
                                .offset(x = barRect.left.toDp(), y = (topMargin + chartHeight).toDp() + 8.dp)
                                .width(barRect.width.toDp())
                        ) {
                            Text(
                                text = info.data.label,
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = config.labelTextSize.sp,
                                color = config.colors.label
                            )
                        }
                    }
                    if (config.part.showEndValues) {
                        Box(
                            modifier = Modifier
                                .offset(x = barRect.left.toDp(), y = (barRect.top - config.valueTextSize.sp.toPx() - 4.dp.toPx()).toDp())
                                .width(barRect.width.toDp())
                        ) {
                            Text(
                                text = "${info.data.value}",
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = config.valueTextSize.sp,
                                color = config.colors.value
                            )
                        }
                    }
                }
            }
        } else { // Horizontal
            // Draw X-Axis Scale
            if (config.part.showXAxis) {
                val steps = 5
                repeat(steps + 1) { i ->
                    val value = (maxValue / steps) * i
                    val x = leftMargin + (chartWidth / steps) * i
                    Box(modifier = Modifier.offset(x = with(density) { (x - 15f).toDp() }, y = with(density) { (topMargin + chartHeight).toDp() + 8.dp })) {
                        Text(
                            text = "${value.toInt()}",
                            fontSize = config.labelTextSize.sp,
                            color = config.colors.value
                        )
                    }
                }
            }
            // Draw Y-Axis Labels and End Values
            barRenderInfo.forEach { info ->
                val barRect = info.rect
                with(density) {
                    if (config.part.showYAxis) {
                        Box(modifier = Modifier.offset(x = 0.dp, y = barRect.top.toDp()).width(leftMargin.toDp() - 8.dp).height(barRect.height.toDp())) {
                            Text(
                                text = info.data.label,
                                modifier = Modifier.align(Alignment.CenterEnd),
                                fontSize = config.labelTextSize.sp,
                                color = config.colors.label
                            )
                        }
                    }
                    if (config.part.showEndValues) {
                        Text(
                            text = "${info.data.value}",
                            modifier = Modifier.offset(x = (barRect.right + 4.dp.toPx()).toDp(), y = (barRect.center.y - (config.valueTextSize.sp.toPx() / 2)).toDp()),
                            fontSize = config.valueTextSize.sp,
                            color = config.colors.value
                        )
                    }
                }
            }
        }
    }
}

private fun calculateBarPositions(
    data: List<BarData>,
    config: ChartConfig,
    chartWidth: Float,
    chartHeight: Float,
    leftMargin: Float,
    topMargin: Float,
    maxValue: Float,
    animationProgress: Float
): List<BarRenderInfo> {
    return when (config.orientation) {
        ChartOrientation.Vertical -> calculateVerticalBarPositions(
            data, chartWidth, chartHeight, leftMargin, topMargin, maxValue, animationProgress, config
        )
        ChartOrientation.Horizontal -> calculateHorizontalBarPositions(
            data, chartWidth, chartHeight, leftMargin, topMargin, maxValue, animationProgress, config
        )
    }
}

private fun calculateVerticalBarPositions(
    data: List<BarData>,
    chartWidth: Float,
    chartHeight: Float,
    leftMargin: Float,
    topMargin: Float,
    maxValue: Float,
    animationProgress: Float,
    config: ChartConfig
): List<BarRenderInfo> {
    val barWidth = (chartWidth / data.size) * config.barPadding
    val barSpacing = chartWidth / data.size

    return data.mapIndexed { index, barData ->
        val normalizedValue = if (maxValue > 0) getFloatValue(barData) / maxValue else 0f
        val barHeight = chartHeight * normalizedValue * animationProgress
        val barLeft = leftMargin + index * barSpacing + (barSpacing - barWidth) / 2
        val barTop = topMargin + chartHeight - barHeight
        BarRenderInfo(Rect(Offset(barLeft, barTop), Size(barWidth, barHeight)), barData)
    }
}

private fun calculateHorizontalBarPositions(
    data: List<BarData>,
    chartWidth: Float,
    chartHeight: Float,
    leftMargin: Float,
    topMargin: Float,
    maxValue: Float,
    animationProgress: Float,
    config: ChartConfig
): List<BarRenderInfo> {
    val barHeight = (chartHeight / data.size) * config.barPadding
    val barSpacing = chartHeight / data.size

    return data.mapIndexed { index, barData ->
        val normalizedValue = if (maxValue > 0) getFloatValue(barData) / maxValue else 0f
        val barWidth = chartWidth * normalizedValue * animationProgress
        val barTop = topMargin + index * barSpacing + (barSpacing - barHeight) / 2
        BarRenderInfo(Rect(Offset(leftMargin, barTop), Size(barWidth, barHeight)), barData)
    }
}