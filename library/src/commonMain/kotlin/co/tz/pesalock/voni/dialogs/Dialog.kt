package co.tz.pesalock.voni.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OverlayDialog(
    modifier: Modifier,
    onDismiss: () -> Unit,
    bar: @Composable BoxScope.() -> Unit,
    content: @Composable BoxScope.() -> Unit
) = BasicAlertDialog(
    modifier = modifier, onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false),
) {
    Box(modifier = Modifier.fillMaxSize()) {
        content()
        bar()
    }
}