package co.tz.pesalock.voni.charts.bars.chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import co.tz.pesalock.voni.charts.bars.setup.ChartConfig

internal fun DrawScope.drawAxes(
    chartWidth: Float,
    chartHeight: Float,
    leftMargin: Float,
    topMargin: Float,
    config: ChartConfig
) {
    val strokeWidth = 2f

    if (config.part.showXAxis) {
        drawLine(
            color = config.colors.axis,
            start = Offset(leftMargin, topMargin + chartHeight),
            end = Offset(leftMargin + chartWidth, topMargin + chartHeight),
            strokeWidth = strokeWidth
        )
    }

    if (config.part.showYAxis) {
        drawLine(
            color = config.colors.axis,
            start = Offset(leftMargin, topMargin),
            end = Offset(leftMargin, topMargin + chartHeight),
            strokeWidth = strokeWidth
        )
    }
}