package co.tz.pesalock.voni.charts.bars.setup

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.dp

data class ShownParts(
    val showXAxis: Boolean = true,
    val showYAxis: Boolean = true,
    val showGrid: Boolean = true,
    val showEndValues: Boolean = false,
) {
    companion object {
        val Default: ShownParts = ShownParts()
    }
}

class Colors(
    val label: Color = Color.Black,
    val value: Color = Color.Black,
    val axis: Color = Color.Black,
    val grid: Color = Color.Gray.copy(alpha = 0.3f)
) {
    companion object {
        val Default: Colors = Colors()
    }
}

data class ChartConfig(
    val part: ShownParts = ShownParts(),
    val orientation: ChartOrientation = ChartOrientation.Horizontal,
    val animationDuration: Int = 2000,
    val colors: Colors = Colors(),
    val labelTextSize: Float = 14f,
    val valueTextSize: Float = 12f,
    val barPadding: Float = 0.6f,
    val resultType: ResultType = IntResult,
    val measurer: TextMeasurer,
    val padding: PaddingValues? = PaddingValues(4.dp),
    val barThickness: Float? = null,
    val paddingBetweenBars: Float? = null
)