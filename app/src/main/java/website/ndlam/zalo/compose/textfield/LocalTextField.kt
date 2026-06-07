package website.ndlam.zalo.compose.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import website.ndlam.zalo.ui.theme.dimes
import website.ndlam.zalo.ui.theme.localTextField
import website.ndlam.zalo.viewmodels.LocalTextFieldViewModel

@Composable
fun LocalTextField(
    viewModel: LocalTextFieldViewModel = viewModel(),
    leftSide: @Composable () -> Unit = {},
    rightSide: @Composable () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLines: Int = 1,
    placeholder: String = "",
    textStyle: TextStyle = TextStyle(
        fontSize = MaterialTheme.dimes.textSize.large,
        color = MaterialTheme.colorScheme.onPrimary,
    ),
    containerHeight: Dp = MaterialTheme.dimes.sizing.xxlarge,
    containerPadding: PaddingValues = PaddingValues(0.dp),
    textFieldPadding: PaddingValues = PaddingValues(0.dp),
    borderWith: Dp = 2.dp
) {
    LocalTextField(
        leftSide = leftSide,
        rightSide = rightSide,
        isFocus = viewModel.focusState.collectAsState().value,
        value = viewModel.value.collectAsState().value,
        onValueChanged = {
            viewModel.setValue(it)
        },
        onFocusChanged = {
            viewModel.setFocus(it)
        },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        maxLines = maxLines,
        placeholder = placeholder,
        textStyle = textStyle,
        containerHeight = containerHeight,
        containerPadding = containerPadding,
        textFieldPadding = textFieldPadding,
        borderWith = borderWith
    )
}

@Composable
fun LocalTextField(
    isFocus: Boolean,
    value: String,
    onValueChanged: (String) -> Unit = {},
    onFocusChanged: (Boolean) -> Unit = {},
    leftSide: @Composable () -> Unit = {},
    rightSide: @Composable () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLines: Int = 1,
    placeholder: String = "",
    textStyle: TextStyle = TextStyle(
        fontSize = MaterialTheme.dimes.textSize.large,
        color = MaterialTheme.colorScheme.onPrimary,
    ),
    containerHeight: Dp = MaterialTheme.dimes.sizing.xxlarge,
    containerPadding: PaddingValues = PaddingValues(0.dp),
    textFieldPadding: PaddingValues = PaddingValues(0.dp),
    borderWith: Dp = 2.dp
) {
    val colorScheme = MaterialTheme.colorScheme.localTextField

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (isFocus) {
            focusRequester.requestFocus()
        } else {
            focusRequester.freeFocus()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(containerHeight)
            .border(
                width = borderWith,
                color = if (isFocus) colorScheme.borderFocus else colorScheme.border,
                RoundedCornerShape(MaterialTheme.dimes.sizing.small)
            )
            .padding(containerPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftSide()
        BasicTextField(
            value = value,
            onValueChange = onValueChanged,
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { focusState ->
                    onFocusChanged(focusState.isFocused)
                }
                .padding(textFieldPadding),
            textStyle = textStyle,
            cursorBrush = colorScheme.cursor,
            maxLines = maxLines,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color.Gray,
                        )
                    }
                    innerTextField()
                }
            },
        )
        rightSide()
    }
}