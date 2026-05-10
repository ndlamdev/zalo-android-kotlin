package website.ndlam.zalo.compose.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.theme.LocalColorScheme
import website.ndlam.zalo.ui.theme.LocalDimens
import website.ndlam.zalo.viewmodels.PhoneNumberViewModel

@Composable
fun PhoneNumberTextField(
    viewModel: PhoneNumberViewModel = viewModel(),
    navigateRegionCode: () -> Unit = {}
) {
    PhoneNumberTextField(
        phoneNumber = viewModel.value.collectAsState().value,
        countryCode = viewModel.regionCode.collectAsState().value,
        onPhoneNumberChange = { newPhoneNumber ->
            viewModel.setValue(newPhoneNumber)
        },
        onClearPhoneNumber = {
            viewModel.setValue("")
        },
        isFocus = viewModel.focusState.collectAsState().value,
        onFocusChanged = { focusState ->
            viewModel.setFocus(focusState)
        },
        navigateRegionCode = navigateRegionCode
    )
}

@Composable
fun PhoneNumberTextField(
    phoneNumber: String = "",
    countryCode: String = "+84",
    onPhoneNumberChange: (String) -> Unit = {},
    onClearPhoneNumber: () -> Unit = {},
    isFocus: Boolean = false,
    onFocusChanged: (Boolean) -> Unit = {},
    navigateRegionCode: () -> Unit = {}
) {
    val colorScheme = LocalColorScheme.current.phoneNumberTextFieldColorScheme

    LocalTextField(
        isFocus = isFocus,
        onFocusChanged = onFocusChanged,
        value = phoneNumber,
        onValueChanged = { value ->
            onPhoneNumberChange(value)
        },
        textFieldPadding = PaddingValues(start = LocalDimens.current.sizing.small),
        placeholder = stringResource(R.string.input_phone_number),
        leftSide = {
            TextButton(
                onClick = navigateRegionCode,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            LocalDimens.current.sizing.small,
                            0.dp,
                            0.dp,
                            LocalDimens.current.sizing.small
                        )
                    )
                    .background(if (isFocus) colorScheme.surfaceFocus else colorScheme.surface)
                    .border(
                        width = 1.dp,
                        if (isFocus) colorScheme.borderCountryCodeFocus else colorScheme.borderCountryCode
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = countryCode,
                        fontSize = LocalDimens.current.textSize.xlarge,
                        color = LocalColorScheme.current.onPrimary,
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_down),
                        contentDescription = null,
                        tint = if (isFocus) colorScheme.iconFocus else LocalColorScheme.current.onPrimary,
                        modifier = Modifier.size(LocalDimens.current.iconSize.sm)
                    )
                }
            }
        },
        rightSide = {
            if (isFocus) {
                IconButton(onClick = onClearPhoneNumber) {
                    Icon(
                        painter = painterResource(R.drawable.ic_cancel_white),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(LocalDimens.current.iconSize.sm - LocalDimens.current.sizing.xsmall)
                            .background(LocalColorScheme.current.onPrimary),
                        tint = LocalColorScheme.current.primary
                    )
                }
            }
        })
}
