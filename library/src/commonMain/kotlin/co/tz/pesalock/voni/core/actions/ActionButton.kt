package io.github.kotlin.fibonacci.co.tz.pesalock.voni.core.actions

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ActionButtonColors(
    val containerColor: Color = Color(0xFF6FC07A),
    val contentColor: Color = Color(0xFFFAFAFA),
    val disabledContainerColor: Color = Color(0xFFBDBDBD),
    val disabledContentColor: Color = Color(0xFFE0E0E0),
)

@Composable
fun ActionButton(
    modifier: Modifier,
    value: String,
    onClick: () -> Unit = {},
    colors: ActionButtonColors = ActionButtonColors()
) = Button(
    onClick = onClick,
    colors = ButtonDefaults.buttonColors(
        disabledContainerColor = colors.disabledContainerColor,
        disabledContentColor = colors.disabledContentColor,
        contentColor = colors.contentColor,
        containerColor = colors.containerColor
    ),
    shape = RoundedCornerShape(15.dp),
    modifier = modifier
) {
    Text(
        text = value,
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .align(Alignment.CenterVertically),
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight(600),
            color = colors.contentColor,
            textAlign = TextAlign.Center,
        )
    )
}