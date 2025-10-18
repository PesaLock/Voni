package co.tz.pesalock.voni.charts.bars.chart

import androidx.annotation.FloatRange
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import co.tz.pesalock.voni.charts.bars.setup.RoundedRectangleShape

fun Size.offsetSize(offset: Offset): Size {
    return Size(
        width = (this.width - offset.x).coerceAtLeast(0f),
        height = (this.height - offset.y).coerceAtLeast(0f)
    )
}

internal fun DrawScope.drawRectangle(
    color: Color,
    topLeft: Offset = Offset.Zero,
    size: Size = this.size.offsetSize(topLeft),
    cornerShape: RoundedRectangleShape = RoundedRectangleShape(),
    style: DrawStyle = Fill,
    @FloatRange(from = 0.0, to = 1.0) alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DefaultBlendMode
) {

    val path = Path().apply {
        val roundRect = RoundRect(
            left = topLeft.x,
            top = topLeft.y,
            right = topLeft.x + size.width,
            bottom = topLeft.y + size.height,
            topLeftCornerRadius = CornerRadius(
                cornerShape.topStart.toPx(),
                cornerShape.topStart.toPx()
            ),
            topRightCornerRadius = CornerRadius(
                cornerShape.topEnd.toPx(),
                cornerShape.topEnd.toPx()
            ),
            bottomLeftCornerRadius = CornerRadius(
                cornerShape.bottomStart.toPx(),
                cornerShape.bottomStart.toPx()
            ),
            bottomRightCornerRadius = CornerRadius(
                cornerShape.bottomEnd.toPx(),
                cornerShape.bottomEnd.toPx()
            )
        )
        addRoundRect(roundRect)
    }

    drawPath(
        path = path,
        color = color,
        style = style,
        alpha = alpha,
        colorFilter = colorFilter,
        blendMode = blendMode
    )
}