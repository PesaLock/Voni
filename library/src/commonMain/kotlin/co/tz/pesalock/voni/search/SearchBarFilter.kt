package majestic.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SearchFilterColors(
    val background: Color,
    val text: Color,
    val hint: Color,
    val border: Color,
    val icon: Color
)
@Deprecated(
    message = "This implementation is deprecated",
    replaceWith = ReplaceWith("majestic.Search")
)
@Composable
fun SearchBarFilter(
    colors: SearchFilterColors,
    query: String,
    hint: String = "Search",
    onQuery: (String) -> Unit,
    onFilter: () -> Unit,
    textStyle: TextStyle? = null,
    iconSearch: ImageVector,
    iconFilter: ImageVector ,
    modifier: Modifier = Modifier
) = Box(modifier = modifier) {
    Row(
        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(0.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = iconSearch,
            contentDescription = "Search",
            tint = colors.icon
        )
        Spacer(Modifier.width(10.dp))
        BasicTextField(
            value = query,
            onValueChange = onQuery,
            singleLine = true,
            textStyle = textStyle ?: TextStyle(color = colors.text, fontSize = 16.sp, textAlign = TextAlign.Start),
            cursorBrush = SolidColor(colors.text.copy(alpha = 0.5f)),
            modifier = Modifier.weight(1f).align(Alignment.CenterVertically),
            decorationBox = { inner ->
                if (query.isEmpty()) {
                    Text(hint, color = colors.hint, fontSize = 16.sp, modifier = Modifier.fillMaxWidth())
                }
                inner()
            }
        )
        VerticalDivider(color = colors.border, thickness = 1.dp, modifier = Modifier.fillMaxHeight())
        Icon(
            imageVector = iconFilter,
            contentDescription = "Filter",
            tint = colors.icon.copy(alpha = 0.8f),
            modifier = Modifier.width(52.dp)
                .fillMaxHeight()
                .wrapContentSize(Alignment.Center)
                .clickable(onClick = onFilter)
        )
    }
}
