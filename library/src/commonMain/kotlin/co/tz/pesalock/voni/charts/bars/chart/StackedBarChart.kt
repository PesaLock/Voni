package co.tz.pesalock.voni.charts.bars.chart

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.tz.pesalock.voni.charts.bars.setup.ChartConfig
import co.tz.pesalock.voni.charts.bars.setup.StackedBarData

private data class StackedBarRenderInfo(
    val rect: Rect,
    val color: Color
)

@Composable
fun StackedBarChart(
    data: List<StackedBarData>,
    modifier: Modifier = Modifier,
    config: ChartConfig,
    backgroundBarColor: Color = Color.White.copy(alpha = 0.05f)
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val animationProgress by animateFloatAsState(
        targetValue = if (animationPlayed) 1f else 0f,
        animationSpec = tween(config.animationDuration, easing = EaseOutCubic),
        label = "stacked_bar_animation"
    )

    LaunchedEffect(Unit) { animationPlayed = true }

    BoxWithConstraints(modifier = modifier) {
        val density = LocalDensity.current
        val layoutDirection = LocalLayoutDirection.current
        val canvasWidth = with(density) { maxWidth.toPx() }
        val canvasHeight = with(density) { maxHeight.toPx() }

        val leftMargin = with(density) { config.padding?.calculateLeftPadding(layoutDirection)?.toPx() ?: 40f }
        val rightMargin = with(density) { config.padding?.calculateRightPadding(layoutDirection)?.toPx() ?: 40f }
        val topMargin = with(density) { config.padding?.calculateTopPadding()?.toPx() ?: 20f }
        val bottomMargin = with(density) { config.padding?.calculateBottomPadding()?.toPx() ?: 40f }

        val chartWidth = canvasWidth - leftMargin - rightMargin
        val chartHeight = canvasHeight - topMargin - bottomMargin
        val barWidth = if (data.isNotEmpty()) (chartWidth / data.size) * config.barPadding else 0f
        val barSpacing = if (data.isNotEmpty()) chartWidth / data.size else 0f

        Canvas(modifier = Modifier.fillMaxSize()) {
            if (chartWidth <= 0 || chartHeight <= 0) return@Canvas
            
            data.forEachIndexed { index, barData ->
                val barLeft = leftMargin + index * barSpacing + (barSpacing - barWidth) / 2
                
                // Draw Full Height Background Bar
                drawRect(
                    color = backgroundBarColor,
                    topLeft = Offset(barLeft, topMargin),
                    size = Size(barWidth, chartHeight)
                )

                // Draw Stacked Segments
                var currentYOffset = 0f
                val totalValue = barData.values.sumOf { it.value.toDouble() }.toFloat().coerceAtLeast(1f)
                
                barData.values.forEach { barValue ->
                    val segmentHeight = chartHeight * (barValue.value / totalValue) * animationProgress
                    val barTop = topMargin + chartHeight - currentYOffset - segmentHeight
                    
                    drawRect(
                        brush = Brush.verticalGradient(
                            listOf(barValue.color, barValue.color.copy(alpha = 0.6f))
                        ),
                        topLeft = Offset(barLeft, barTop),
                        size = Size(barWidth, segmentHeight)
                    )
                    currentYOffset += segmentHeight
                }
            }
        }

        // Labels
        if (config.part.showXAxis) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = with(density) { leftMargin.toDp() }, end = with(density) { rightMargin.toDp() })
                    .offset(y = with(density) { (topMargin + chartHeight).toDp() + 8.dp }),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                data.forEach { barData ->
                    Text(
                        text = barData.label,
                        fontSize = config.labelTextSize.sp,
                        color = config.colors.label.copy(alpha = 0.8f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
