package co.tz.pesalock.voni.charts.bars.chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import co.tz.pesalock.voni.charts.bars.getFloatValue
import co.tz.pesalock.voni.charts.bars.setup.BarData
import co.tz.pesalock.voni.charts.bars.setup.ChartConfig

internal fun DrawScope.drawVerticalBars(
    data: List<BarData>,
    chartWidth: Float,
    chartHeight: Float,
    leftMargin: Float,
    topMargin: Float,
    maxValue: Float,
    animationProgress: Float,
    config: ChartConfig
) {
    if (data.isEmpty()) return

    val barWidth: Float
    val barSlotWidth: Float
    val startX: Float

    if (config.barThickness != null && config.paddingBetweenBars != null) {
        barWidth = config.barThickness
        barSlotWidth = barWidth + config.paddingBetweenBars
        val totalWidth = data.size * barSlotWidth - if (data.size > 1) config.paddingBetweenBars else 0f
        startX = leftMargin + (chartWidth - totalWidth) / 2f
    } else {
        barSlotWidth = chartWidth / data.size
        barWidth = barSlotWidth * config.barPadding
        startX = leftMargin
    }

    data.forEachIndexed { index, barData ->
        val normalizedValue = if (maxValue > 0) getFloatValue(barData) / maxValue else 0f
        val barHeight = chartHeight * normalizedValue * animationProgress

        val barLeft = startX + index * barSlotWidth + (barSlotWidth - barWidth) / 2
        val barTop = topMargin + chartHeight - barHeight

        drawBarShape(
            shape = barData.shape,
            color = barData.color,
            topLeft = Offset(barLeft, barTop),
            size = Size(barWidth, barHeight)
        )

        val labelWidth = barData.label.length * config.labelTextSize * 0.5f
        drawText(
            topLeft = Offset(barLeft + barWidth / 2 - labelWidth / 2, topMargin + chartHeight + 15f),
            text = barData.label,
            style = TextStyle(
                color = config.colors.label,
                fontSize = config.labelTextSize.sp,
                fontWeight = FontWeight.Black
            ),
            overflow = TextOverflow.Ellipsis,
            textMeasurer = config.measurer
        )

        if (!config.part.showYAxis || config.part.showEndValues) {
            val valueText = "${barData.value}"
            val valueWidth = valueText.length * config.valueTextSize * 0.5f
            drawText(
                topLeft = Offset(barLeft + barWidth / 2 - valueWidth / 2, barTop - 20f),
                text = valueText,
                style = TextStyle(
                    color = config.colors.value,
                    fontSize = config.valueTextSize.sp,
                    fontWeight = FontWeight.Normal
                ),
                overflow = TextOverflow.Ellipsis,
                textMeasurer = config.measurer
            )
        }
    }

    if (config.part.showYAxis) {
        val steps = 5
        repeat(steps + 1) { i ->
            val value = (maxValue / steps) * i
            val y = topMargin + chartHeight - (chartHeight / steps) * i
            val valueText = "$value"
            drawText(
                topLeft = Offset(10f, y - config.valueTextSize / 2),
                text = valueText,
                style = TextStyle(
                    color = config.colors.value,
                    fontSize = config.valueTextSize.sp,
                    fontWeight = FontWeight.Normal
                ),
                overflow = TextOverflow.Ellipsis,
                textMeasurer = config.measurer
            )
        }
    }
}