package co.tz.pesalock.voni.charts.bars

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import co.tz.pesalock.voni.charts.bars.setup.BarData
import co.tz.pesalock.voni.charts.bars.setup.FloatResult
import co.tz.pesalock.voni.charts.bars.setup.IntResult
import co.tz.pesalock.voni.charts.bars.setup.ResultType

internal fun calculateLabelWidth(labels: List<String>, textSize: Float): Float {
    val longestLabel = labels.maxByOrNull { it.length }?.length ?: 0
    return longestLabel * textSize * 0.6f
}


@Composable
internal fun dataConversion(
    data: List<BarData>,
    resultType: ResultType = IntResult
): List<BarData> = remember(data, resultType) {
    data.map { barData ->
        val convertedValue = when (val value = barData.value) {
            is Number -> {
                when (resultType) {
                    is FloatResult -> value.toFloat()
                    is IntResult -> value.toInt()
                }
            }

            is String -> {
                when (resultType) {
                    is FloatResult -> value.toFloatOrNull() ?: 0f
                    is IntResult -> value.toIntOrNull() ?: 0
                }
            }

            else -> {
                when (resultType) {
                    is FloatResult -> 0f
                    is IntResult -> 0
                }
            }
        }
        barData.copy(value = convertedValue)
    }
}

internal fun getFloatValue(barData: BarData): Float {
    return when (val value = barData.value) {
        is Float -> value
        is Int -> value.toFloat()
        is Double -> value.toFloat()
        is Long -> value.toFloat()
        is Short -> value.toFloat()
        is Byte -> value.toFloat()
        else -> 0f
    }
}