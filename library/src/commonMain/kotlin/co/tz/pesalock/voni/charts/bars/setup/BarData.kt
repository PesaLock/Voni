package co.tz.pesalock.voni.charts.bars.setup

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

public data class BarData(
    val label: String,
    val value: Any,
    val color: Color = Color.Blue,
    val shape: BarShape = RoundedRectangleShape(topEnd = 10.dp, bottomEnd = 10.dp)
)