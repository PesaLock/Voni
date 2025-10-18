package co.tz.pesalock.voni.charts.bars.chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import co.tz.pesalock.voni.charts.bars.setup.ChartOrientation

internal fun DrawScope.drawGrid(
    chartWidth: Float,
    chartHeight: Float,
    leftMargin: Float,
    topMargin: Float,
    color: Color,
    orientation: ChartOrientation
) {
    val gridLines = 5
    val strokeWidth = 1f

    when (orientation) {
        ChartOrientation.Horizontal -> {
            // Vertical grid lines
            repeat(gridLines + 1) { i ->
                val x = leftMargin + (chartWidth / gridLines) * i
                drawLine(
                    color = color,
                    start = Offset(x, topMargin),
                    end = Offset(x, topMargin + chartHeight),
                    strokeWidth = strokeWidth
                )
            }

            // Horizontal grid lines
            repeat(6) { i ->
                val y = topMargin + (chartHeight / 5) * i
                drawLine(
                    color = color,
                    start = Offset(leftMargin, y),
                    end = Offset(leftMargin + chartWidth, y),
                    strokeWidth = strokeWidth
                )
            }
        }

        ChartOrientation.Vertical -> {
            // Horizontal grid lines
            repeat(gridLines + 1) { i ->
                val y = topMargin + (chartHeight / gridLines) * i
                drawLine(
                    color = color,
                    start = Offset(leftMargin, y),
                    end = Offset(leftMargin + chartWidth, y),
                    strokeWidth = strokeWidth
                )
            }

            // Vertical grid lines
            repeat(6) { i ->
                val x = leftMargin + (chartWidth / 5) * i
                drawLine(
                    color = color,
                    start = Offset(x, topMargin),
                    end = Offset(x, topMargin + chartHeight),
                    strokeWidth = strokeWidth
                )
            }
        }
    }
}