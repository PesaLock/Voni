package co.tz.pesalock.voni.indicators

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.tz.pesalock.voni.theme.ThemeColors

@Composable
fun CircularProgressOverlay(
    modifier: Modifier = Modifier,
    theme: ThemeColors
) = Box(
    modifier = modifier, contentAlignment = Alignment.Center
) {
    CircularProgressIndicator(
        modifier = Modifier.size(39.dp),
        color = theme.primary.support
    )
}