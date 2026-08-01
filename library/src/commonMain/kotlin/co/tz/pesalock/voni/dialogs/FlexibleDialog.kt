package co.tz.pesalock.voni.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun FlexibleDialog(
    modifier: Modifier,
    onDismiss: () -> Unit,
    bar: @Composable ColumnScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) = BasicAlertDialog(
    modifier = modifier, onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false),
) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        bar()
        content()
    }
}
