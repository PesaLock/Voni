//package majestic.search
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.onFocusChanged
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.SolidColor
//import androidx.compose.ui.graphics.compositeOver
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.unit.sp
//
//
//@Composable
//fun SearchField(
//    theme: ThemeColor,
//    modifier: Modifier,
//    hint: String = "Search",
//    value: String?,
//    onChange: (String) -> Unit,
//    icon: @Composable () -> Unit
//) = Row(
//    modifier = modifier,
//    verticalAlignment = Alignment.CenterVertically
//) {
//    var text by remember { mutableStateOf(value.orEmpty()) }
//    var showHint by remember { mutableStateOf(false) }
//
//    BasicTextField(
//        value = text,
//        onValueChange = { new ->
//            text = new
//            onChange(new)
//        },
//        modifier = Modifier.weight(1f).onFocusChanged { showHint = it.isFocused },
//        singleLine = true,
//        cursorBrush = SolidColor(theme.toSearchColors().text),
//        textStyle = TextStyle(color = theme.toSearchColors().text, fontSize = 14.sp),
//        decorationBox = { inner ->
//            Box(
//                modifier = Modifier.fillMaxWidth(),
//                contentAlignment = Alignment.CenterStart
//            ) {
//                if (!showHint && text.isEmpty()) Text(
//                    text = hint,
//                    color = theme.toSearchColors().hint,
//                    fontSize = 14.sp
//                ) else inner()
//            }
//        }
//    )
//    icon()
//}