package co.tz.pesalock.voni.table

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
) = Column(modifier = modifier) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        columnWeights.forEach { (title, weight) ->
            head(title, weight)
        }
    }
    LazyColumn(
        modifier = Modifier.weight(1f).fillMaxWidth()
    ) {


        items(table) { item ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                columnWeights.forEach { (title, weight) ->
                    body(item, title, weight)
                }
            }
        }
    }
}