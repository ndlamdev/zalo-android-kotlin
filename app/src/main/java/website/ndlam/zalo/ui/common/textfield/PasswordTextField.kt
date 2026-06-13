package website.ndlam.zalo.ui.common.textfield

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.theme.dimes
import website.ndlam.zalo.ui.common.textfield.viewmodel.PasswordTextFieldViewModel

@Composable
fun PasswordTextField(
    viewModel: PasswordTextFieldViewModel = viewModel()
) {
    val hidden = viewModel.hidden.collectAsState()

    PasswordTextField(
        password = viewModel.value.collectAsState().value,
        isFocus = viewModel.focusState.collectAsState().value,
        onFocusChanged = { focusState ->
            viewModel.setFocus(focusState)
        },
        onPasswordChange = { newPassword ->
            viewModel.setValue(newPassword)
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
    onFocusChanged: (Boolean) -> Unit = {},
    hidden: Boolean = false,
    onShowOrHidden: () -> Unit = {}
) {
    LocalTextField(
        isFocus = isFocus, value = password,
        textFieldPadding = PaddingValues(start = MaterialTheme.dimes.sizing.small),
        onValueChanged = { text ->
            onPasswordChange(text)
        },
        onFocusChanged = onFocusChanged,
        visualTransformation = if (!hidden) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        placeholder = stringResource(R.string.input_password),
        rightSide = {
            IconButton(
                onClick = {
                    onShowOrHidden()
                }, modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            0.dp,
                            MaterialTheme.dimes.sizing.small,
                            MaterialTheme.dimes.sizing.small,
                            0.dp
                        )
                    )
            ) {
                Icon(
                    painter = painterResource(if (hidden) R.drawable.ic_eye else R.drawable.ic_eye_closed),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        })
}