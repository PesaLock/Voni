package co.tz.pesalock.voni.tools.flexible

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import co.tz.pesalock.voni.orientation.Landscape
import co.tz.pesalock.voni.orientation.Portrait
import co.tz.pesalock.voni.orientation.ScreenOrientation


private class FlexibleColumnScope(private val scope: ColumnScope) : FlexibleScope {
    override fun Modifier.flex(value: Float): Modifier = with(scope) {
        weight(value)
    }
}

private class FlexibleRowScope(private val scope: RowScope) : FlexibleScope {
    override fun Modifier.flex(value: Float): Modifier = with(scope) {
        weight(value)
    }
}

@Composable
fun Flexible(
    orientation: ScreenOrientation,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable FlexibleScope.() -> Unit
) {
    when (orientation) {
        is Portrait -> Column(
            modifier = modifier,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            FlexibleColumnScope(this).content()
        }


        is Landscape -> Row(
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment
        ) {
            FlexibleRowScope(this).content()
        }
    }
}
