package co.tz.pesalock.voni.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * A generic table component using LazyColumn
 *
 * @param T The type of data objects in the table
 * @param columnWeights Map of column titles to their weights
 * @param table List of data objects to display
 * @param modifier Modifier for the table container
 * @param head Composable content for the header row
 * @param body Composable content for each data row
 */
@Composable
fun <T> LazyTable(
    columnWeights: Map<String, Float>,
    table: List<T>,
    modifier: Modifier = Modifier,
    head: @Composable RowScope.(title: String, weight: Float) -> Unit,
    body: @Composable RowScope.(item: T, title: String, weight: Float) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                columnWeights.forEach { (title, weight) ->
                    head(title, weight)
                }
            }
        }

        items(table) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                columnWeights.forEach { (title, weight) ->
                    body(item, title, weight)
                }
            }
        }
    }
}