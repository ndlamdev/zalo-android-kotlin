package website.ndlam.zalo.ui.feature.password

import android.widget.Toast
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import website.ndlam.zalo.R
import website.ndlam.zalo.core.util.formater.PhoneNumberFormater
import website.ndlam.zalo.data.remote.api.ApiState
import website.ndlam.zalo.data.repository.AuthTokenRepositoryImpl
import website.ndlam.zalo.domain.repository.IAuthTokenRepository
import website.ndlam.zalo.ui.common.textfield.PasswordTextField
import website.ndlam.zalo.ui.common.textfield.viewmodel.PasswordTextFieldViewModel
import website.ndlam.zalo.ui.theme.Blue800
import website.ndlam.zalo.ui.theme.SuperWhite
import website.ndlam.zalo.ui.theme.dimes
import website.ndlam.zalo.ui.theme.disableButton
import website.ndlam.zalo.ui.theme.onDisableButton
import website.ndlam.zalo.ui.theme.passwordScreen

@Composable
fun PasswordScreen(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onBackPress: () -> Unit = {},
    swissNumber: String,
    regionCode: String,
    onLoginSuccess: () -> Unit = {},
    authTokenRepository: IAuthTokenRepository
) {
    val textFiledViewModel = viewModel<PasswordTextFieldViewModel>()
    val context = LocalContext.current
    val loginFailedMessage = stringResource(R.string.login_failed)
    val viewModel = viewModel<PasswordViewModel>(
        factory = viewModelFactory {
            initializer {
                PasswordViewModel(authTokenRepository)
            }
        }
    )

    val password = textFiledViewModel.value.collectAsState()
    val loginStatus = viewModel.loginStatus.collectAsState()

    LaunchedEffect(loginStatus.value) {
        when (val value = loginStatus.value) {
            is ApiState.Error<*> ->
                Toast.makeText(context, value.message, Toast.LENGTH_SHORT).show()

            is ApiState.Loading<*> -> {
                // TODO("Handler ui when loading")
            }

            else -> onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(paddingValues)
            .padding(MaterialTheme.dimes.sizing.medium),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_white_arrow_left),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(MaterialTheme.dimes.iconSize.md)
                .clickable(onClick = onBackPress)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimes.sizing.medium))
        Text(
            text = stringResource(R.string.input_password_for_this_phone_number),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = PhoneNumberFormater.nationalFormat(
                swissNumber.toLong(),
                regionCode.replace("+", "").toInt()
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.dimes.sizing.small,
                    bottom = MaterialTheme.dimes.sizing.large
                ),
            fontSize = MaterialTheme.dimes.textSize.xxlarge,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = Bold
        )

        PasswordTextField(textFiledViewModel)

        Spacer(modifier = Modifier.height(MaterialTheme.dimes.sizing.large))

        TextButton(
            enabled = password.value.isNotEmpty(),
            contentPadding = PaddingValues(vertical = MaterialTheme.dimes.sizing.textButtonVerticalPadding),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Blue800,
                disabledContainerColor = MaterialTheme.colorScheme.disableButton,
                contentColor = SuperWhite,
                disabledContentColor = MaterialTheme.colorScheme.onDisableButton
            ),
            onClick = {
                viewModel.login(
                    PhoneNumberFormater.e164Format(
                        swissNumber.toLong(),
                        regionCode.replace("+", "").toInt()
                    ), password.value, loginFailedMessage
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(MaterialTheme.dimes.sizing.large))
                .background(if (password.value.isEmpty()) MaterialTheme.colorScheme.disableButton else Blue800)
        ) {
            Text(text = stringResource(R.string.text_continue))
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.forget_password),
                color = MaterialTheme.colorScheme.passwordScreen.forgetPassword,
                fontWeight = Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable(
                    onClick = {}
                ),
                fontSize = MaterialTheme.dimes.textSize.large
            )
        }
    }
}

