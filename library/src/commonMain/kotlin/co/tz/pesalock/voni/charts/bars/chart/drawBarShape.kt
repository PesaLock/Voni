package co.tz.pesalock.voni.charts.bars.chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import co.tz.pesalock.voni.charts.bars.setup.BarShape
import co.tz.pesalock.voni.charts.bars.setup.Rectangle
import co.tz.pesalock.voni.charts.bars.setup.RoundedRectangleShape

internal fun DrawScope.drawBarShape(
    shape: BarShape,
    color: Color,
    topLeft: Offset,
    size: Size
) {
    when (shape) {
        is Rectangle -> {
            drawRect(
                color = color,
                topLeft = topLeft,
                size = size
            )
        }

        is RoundedRectangleShape -> {
            drawRectangle(
                color = color,
                topLeft = topLeft,
                size = size,
                cornerShape = shape
            )
        }
    }
}