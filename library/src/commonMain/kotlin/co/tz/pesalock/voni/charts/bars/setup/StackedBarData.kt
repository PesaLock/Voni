package co.tz.pesalock.voni.charts.bars.setup

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

public data class StackedBarData(
    val label: String,
    val values: List<StackedBarValue>,
    val shape: BarShape = RoundedRectangleShape(topStart = 4.dp, topEnd = 4.dp)
)

public data class StackedBarValue(
    val value: Float,
    val color: Color,
    val label: String? = null
)
