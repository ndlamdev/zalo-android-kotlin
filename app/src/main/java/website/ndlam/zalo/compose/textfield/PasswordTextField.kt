package website.ndlam.zalo.compose.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.theme.LocalColorScheme
import website.ndlam.zalo.ui.theme.LocalDimens
import website.ndlam.zalo.viewmodels.PasswordTextFieldViewModel

@Composable
fun PasswordTextField(
    viewModel: PasswordTextFieldViewModel = viewModel()
) {
    val hidden = viewModel.hidden.collectAsState()

    PasswordTextField(
        password = viewModel.password.collectAsState().value,
        isFocus = viewModel.focusState.collectAsState().value,
        onFocusChanged = { focusState ->
            viewModel.updateFocus(focusState.isFocused)
        },
        onPasswordChange = { newPassword ->
            viewModel.updatePassword(newPassword)
        },
        hidden = hidden.value,
        onShowOrHidden = {
            if (hidden.value) {
                viewModel.show()
            } else {
                viewModel.hide()
            }
        }
    )
}

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (password: String) -> Unit = {},
    isFocus: Boolean,
    onFocusChanged: (FocusState) -> Unit = {},
    hidden: Boolean = false,
    onShowOrHidden: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    val colorScheme = LocalColorScheme.current.passwordTextFieldColorScheme

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
            .height(LocalDimens.current.sizing.xxlarge)
            .border(
                width = 2.dp,
                color = if (isFocus) colorScheme.borderFocus else colorScheme.border,
                RoundedCornerShape(LocalDimens.current.sizing.small)
            )
            .padding(start = LocalDimens.current.sizing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester)
                .onFocusChanged(onFocusChanged),
            textStyle = TextStyle(
                fontSize = LocalDimens.current.textSize.large,
                color = LocalColorScheme.current.onPrimary,
            ),
            cursorBrush = colorScheme.cursor,
            maxLines = 1,
            visualTransformation = if (!hidden) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (password.isEmpty()) {
                        Text(
                            text = stringResource(R.string.input_password),
                            color = Color.Gray,
                        )
                    }
                    innerTextField()
                }
            },
        )
        IconButton(
            onClick = {
                onShowOrHidden()
            }, modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        0.dp,
                        LocalDimens.current.sizing.small,
                        LocalDimens.current.sizing.small,
                        0.dp
                    )
                )
        ) {
            Icon(
                painter = painterResource(if (hidden) R.drawable.ic_eye else R.drawable.ic_eye_closed),
                contentDescription = null,
                tint = LocalColorScheme.current.onPrimary
            )
        }
    }
}