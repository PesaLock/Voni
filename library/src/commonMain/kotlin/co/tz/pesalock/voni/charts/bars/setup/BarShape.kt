package co.tz.pesalock.voni.charts.bars.setup

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

public sealed interface BarShape
public data class RoundedRectangleShape(
    val topStart: Dp = 0.dp,
    val topEnd: Dp = 0.dp,
    val bottomStart: Dp = 0.dp,
    val bottomEnd: Dp = 0.dp
) : BarShape

public object Rectangle : BarShape