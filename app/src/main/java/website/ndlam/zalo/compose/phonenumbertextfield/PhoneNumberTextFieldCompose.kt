package website.ndlam.zalo.compose.phonenumbertextfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.theme.LocalColorScheme
import website.ndlam.zalo.ui.theme.LocalDimens
import website.ndlam.zalo.viewmodels.PhoneNumberViewModel

@Composable
fun PhoneNumberTextField(
    viewModel: PhoneNumberViewModel = viewModel()
) {
    PhoneNumberTextField(
        phoneNumber = viewModel.phoneNumber.collectAsState().value,
        countryCode = viewModel.countryCode.collectAsState().value,
        onPhoneNumberChange = { newPhoneNumber ->
            viewModel.updatePhoneNumber(newPhoneNumber)
        },
        onClearPhoneNumber = {
            viewModel.updatePhoneNumber("")
        }
    )
}

@Composable
fun PhoneNumberTextField(
    phoneNumber: String = "",
    countryCode: String = "+84",
    onPhoneNumberChange: (String) -> Unit = {},
    onClearPhoneNumber: () -> Unit = {},
) {
    val colorScheme = LocalColorScheme.current.phoneNumberTextFieldColorScheme

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = colorScheme.border,
                RoundedCornerShape(LocalDimens.current.sizing.small)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = {},
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        LocalDimens.current.sizing.small,
                        0.dp,
                        0.dp,
                        LocalDimens.current.sizing.small
                    )
                )
                .background(colorScheme.surface)
                .border(width = 1.dp, colorScheme.borderText)
        ) {
            Row(
                modifier = Modifier
                    .width(LocalDimens.current.sizing.large * 2)
                    .height(LocalDimens.current.sizing.large + LocalDimens.current.sizing.xsmall),
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
                    tint = colorScheme.icon,
                    modifier = Modifier.size(LocalDimens.current.iconSize.sm)
                )
            }
        }

        BasicTextField(
            value = phoneNumber,
            onValueChange = { newText ->
                onPhoneNumberChange(if (newText.length > 15) newText.substring(0, 15) else newText)
            },
            modifier = Modifier
                .padding(LocalDimens.current.sizing.small)
                .weight(1f),
            textStyle = TextStyle(
                fontSize = LocalDimens.current.textSize.xlarge,
                color = LocalColorScheme.current.onPrimary,
            ),
            cursorBrush = colorScheme.cursor,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        )

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
}
