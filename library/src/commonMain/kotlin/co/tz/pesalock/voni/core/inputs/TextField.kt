package co.tz.pesalock.voni.core.inputs

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TextFieldColors(
    val label: StateColors = StateColors(
        active = Color(0xFF000000),
        inActive = Color(0xFF000000).copy(.04f)
    ),
    val hint: StateColors = StateColors(
        active = Color(0xFF000000),
        inActive = Color(0xFF000000).copy(.04f)
    ),
    val text: StateColors = StateColors(
        active = Color(0xFF000000),
        inActive = Color(0xFF000000).copy(.04f)
    ),
    val border: StateColors = StateColors(
        active = Color(0xFF000000),
        inActive = Color(0xFF000000).copy(.04f)
    ),
    val container: StateColors = StateColors(
        active = Color(0xFF000000),
        inActive = Color(0xFF000000).copy(.04f)
    )
)

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String? = null,
    minLines: Int = 1,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    label: String? = null,
    isError: Boolean = false,
    colors: TextFieldColors = TextFieldColors(),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    leadIcon: @Composable (() -> Unit)? = null,
    trailIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = { Text(hint ?: "", color = colors.hint.inActive, fontSize = 13.sp) },
    shape: Shape = RoundedCornerShape(8.dp)
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalAlignment = Alignment.Start
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    if (label != null) Text(
        text = label,
        color = if (focused) colors.label.active else colors.label.inActive,
        fontSize = 14.sp,
    )
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = TextStyle(
            fontSize = 14.sp,
            color = if (focused) colors.text.active else colors.text.inActive,
        ),
        placeholder = placeholder,
        leadingIcon = leadIcon,
        trailingIcon = trailIcon,
        prefix = prefix,
        suffix = suffix,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = colors.text.active,
            unfocusedTextColor = colors.text.inActive,
            focusedBorderColor = colors.border.active,
            unfocusedBorderColor = colors.border.inActive,
            focusedContainerColor = colors.container.inActive,
            unfocusedContainerColor = colors.container.inActive,
        ),
        supportingText = supportingText,
    )
}