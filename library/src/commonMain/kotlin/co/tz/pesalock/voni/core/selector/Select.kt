package co.tz.pesalock.voni.core.selector


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.tz.pesalock.voni.tools.StateColors

data class DropDownConfig(
    val modifier: Modifier? = null,
    val containerColor: Color = Color.White,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> Selector(
    items: List<T>,
    selectedItem: T,
    dropdownConfig: DropDownConfig = DropDownConfig(),
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    selectorButton: @Composable (
        selectedItem: T,
        expanded: Boolean,
        modifier: Modifier
    ) -> Unit = { item, exp, mod ->
        DefaultSelectorButton(
            selectedItem = item,
            expanded = exp,
            modifier = mod,
            itemLabel = { it.toString() }
        )
    },
    dropdownItem: @Composable (
        item: T,
        onClick: () -> Unit
    ) -> Unit = { item, click ->
        DefaultDropdownItem(
            item = item,
            onClick = click,
            itemLabel = { it.toString() }
        )
    }
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        selectorButton(
            selectedItem,
            expanded,
            Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            modifier = Modifier.then(
                dropdownConfig.modifier ?: Modifier
                    .exposedDropdownSize()
                    .background(dropdownConfig.containerColor)
            ),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                dropdownItem(item) {
                    onItemSelected(item)
                    expanded = false
                }
            }
        }
    }
}

// Default implementations for backward compatibility
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> DefaultSelectorButton(
    selectedItem: T,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    itemLabel: (T) -> String,
    colors: SelectorColors = SelectorDefaults.defaultColors()
) {
    Box(
        modifier = modifier
            .border(
                1.dp,
                color = colors.borderColor.inactive,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        TextField(
            value = itemLabel(selectedItem),
            onValueChange = { },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                unfocusedContainerColor = colors.containerColor.inactive,
                focusedContainerColor = colors.containerColor.active,
                unfocusedTextColor = colors.textColor.inactive,
                focusedTextColor = colors.textColor.active,
                focusedTrailingIconColor = colors.trailingIconColor.active,
                unfocusedTrailingIconColor = colors.trailingIconColor.inactive,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
private fun <T> DefaultDropdownItem(
    item: T,
    onClick: () -> Unit,
    itemLabel: (T) -> String,
    colors: SelectorColors = SelectorDefaults.defaultColors()
) {
    DropdownMenuItem(
        text = {
            Text(
                text = itemLabel(item),
                color = colors.itemTextColor.inactive
            )
        },
        onClick = onClick
    )
}

// Color configuration
data class SelectorColors(
    val borderColor: StateColors,
    val containerColor: StateColors,
    val textColor: StateColors,
    val trailingIconColor: StateColors,
    val itemTextColor: StateColors,
    val dropdownColor: Color
)

object SelectorDefaults {
    @Composable
    fun defaultColors(
        borderColor: StateColors = StateColors(
            active = Color.Black,
            inactive = Color.LightGray
        ),
        containerColor: StateColors = StateColors(
            active = Color.White,
            inactive = Color.LightGray
        ),
        textColor: StateColors = StateColors(
            active = Color.Black,
            inactive = Color.LightGray
        ),
        trailingIconColor: StateColors = StateColors(
            active = Color.Black,
            inactive = Color.LightGray
        ),
        itemTextColor: StateColors = StateColors(
            active = Color.Black,
            inactive = Color.LightGray
        )
    ): SelectorColors {
        return SelectorColors(
            borderColor = borderColor,
            containerColor = containerColor,
            textColor = textColor,
            trailingIconColor = trailingIconColor,
            itemTextColor = itemTextColor,
            dropdownColor = Color.White
        )
    }
}

// Example usage with custom designs
/*
// Usage Example 1: Simple with default design
Selector(
    items = listOf("Option 1", "Option 2", "Option 3"),
    selectedItem = "Option 1",
    onItemSelected = { selected -> }
)

// Usage Example 2: Custom button and items
Selector(
    items = users,
    selectedItem = currentUser,
    onItemSelected = { user -> },
    selectorButton = { user, expanded, modifier ->
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Person, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(user.name)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    if (expanded) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }
    },
    dropdownItem = { user, onClick ->
        DropdownMenuItem(
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = user.avatar,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp).clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(user.name, style = MaterialTheme.typography.bodyLarge)
                        Text(user.email, style = MaterialTheme.typography.bodySmall)
                    }
                }
            },
            onClick = onClick
        )
    }
)
*/