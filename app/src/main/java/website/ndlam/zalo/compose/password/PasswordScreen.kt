package website.ndlam.zalo.compose.password

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import website.ndlam.zalo.R
import website.ndlam.zalo.compose.textfield.PasswordTextField
import website.ndlam.zalo.ui.theme.Blue800
import website.ndlam.zalo.ui.theme.LocalColorScheme
import website.ndlam.zalo.ui.theme.LocalDimens
import website.ndlam.zalo.ui.theme.SuperWhite
import website.ndlam.zalo.viewmodels.PasswordTextFieldViewModel

@Composable
fun PasswordScreen(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onBackPress: () -> Unit = {},
    phoneNumber: String,
    onContinuePress: () -> Unit = {}
) {
    val viewModel = viewModel<PasswordTextFieldViewModel>()
    val password = viewModel.value.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalColorScheme.current.primary)
            .padding(paddingValues)
            .padding(LocalDimens.current.sizing.medium),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_white_arrow_left),
            contentDescription = null,
            tint = LocalColorScheme.current.onPrimary,
            modifier = Modifier
                .size(LocalDimens.current.iconSize.md)
                .clickable(onClick = onBackPress)
        )

        Spacer(modifier = Modifier.height(LocalDimens.current.sizing.medium))
        Text(
            text = stringResource(R.string.input_password_for_this_phone_number),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = LocalColorScheme.current.onPrimary
        )
        Text(
            text = phoneNumber,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = LocalDimens.current.sizing.small,
                    bottom = LocalDimens.current.sizing.large
                ),
            fontSize = LocalDimens.current.textSize.xxlarge,
            color = LocalColorScheme.current.onPrimary,
            fontWeight = Bold
        )

        PasswordTextField(viewModel)

        Spacer(modifier = Modifier.height(LocalDimens.current.sizing.large))

        TextButton(
            onClick = {
                if (password.value.isNotEmpty()) {
                    onContinuePress()
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(LocalDimens.current.sizing.large))
                .background(if (password.value.isEmpty()) LocalColorScheme.current.disableButton else Blue800)
        ) {
            Text(
                text = stringResource(R.string.text_continue),
                color = if (viewModel.value.collectAsState().value.isEmpty()) LocalColorScheme.current.onDisableButton else SuperWhite
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.forget_password),
                color = LocalColorScheme.current.passwordScreenColorScheme.forgetPassword,
                fontWeight = Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable(
                    onClick = {}
                ),
                fontSize = LocalDimens.current.textSize.large
            )
        }
    }
}

