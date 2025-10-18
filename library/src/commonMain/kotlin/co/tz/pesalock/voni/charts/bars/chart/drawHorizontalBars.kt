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

internal fun DrawScope.drawHorizontalBars(
    data: List<BarData>,
    chartWidth: Float,
    chartHeight: Float,
    leftMargin: Float,
    topMargin: Float,
    maxValue: Float,
    animationProgress: Float,
    config: ChartConfig,
) {
    if (data.isEmpty()) return

    val barHeight: Float
    val barSlotHeight: Float
    val startY: Float

    if (config.barThickness != null && config.paddingBetweenBars != null) {
        barHeight = config.barThickness
        barSlotHeight = barHeight + config.paddingBetweenBars
        val totalHeight = data.size * barSlotHeight - if (data.size > 1) config.paddingBetweenBars else 0f
        startY = topMargin + (chartHeight - totalHeight) / 2f
    } else {
        barSlotHeight = chartHeight / data.size
        barHeight = barSlotHeight * config.barPadding
        startY = topMargin
    }

    data.forEachIndexed { index, barData ->
        val normalizedValue = if (maxValue > 0) getFloatValue(barData) / maxValue else 0f
        val barWidth = chartWidth * normalizedValue * animationProgress

        val barTop = startY + index * barSlotHeight + (barSlotHeight - barHeight) / 2

        drawBarShape(
            shape = barData.shape,
            color = barData.color,
            topLeft = Offset(leftMargin, barTop),
            size = Size(barWidth, barHeight)
        )

        val textCenterY = barTop + barHeight / 2
        drawText(
            topLeft = Offset(10f, textCenterY - config.labelTextSize / 2),
            text = barData.label,
            style = TextStyle(
                color = config.colors.label,
                fontSize = config.labelTextSize.sp,
                fontWeight = FontWeight.Normal
            ),
            overflow = TextOverflow.Ellipsis,
            textMeasurer = config.measurer
        )

        if (!config.part.showXAxis || config.part.showEndValues) {
            val valueText = "${barData.value}"
            val valueTextLayout =
                config.measurer.measure(valueText, style = TextStyle(fontSize = config.valueTextSize.sp))
            val valueTextHeight = valueTextLayout.size.height.toFloat()
            val valueTextY = textCenterY - valueTextHeight / 2
            drawText(
                topLeft = Offset(leftMargin + barWidth + 12f, valueTextY),
                text = valueText,
                style = TextStyle(
                    color = config.colors.value,
                    fontSize = config.valueTextSize.sp,
                    fontWeight = FontWeight.Normal
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textMeasurer = config.measurer
            )
        }
    }

    if (config.part.showXAxis) {
        val steps = 5
        repeat(steps + 1) { i ->
            val value = (maxValue / steps) * i
            val x = leftMargin + (chartWidth / steps) * i
            val valueText = "$value"
            drawText(
                topLeft = Offset(x - 15f, topMargin + chartHeight + 10f),
                text = valueText,
                style = TextStyle(
                    color = config.colors.label,
                    fontSize = config.labelTextSize.sp,
                    fontWeight = FontWeight.Normal
                ),
                overflow = TextOverflow.Ellipsis,
                textMeasurer = config.measurer
            )
        }
    }
}