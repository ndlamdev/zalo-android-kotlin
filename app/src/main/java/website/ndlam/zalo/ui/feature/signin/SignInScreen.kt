package website.ndlam.zalo.ui.feature.signin

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import website.ndlam.zalo.ui.common.textfield.PhoneNumberTextField
import website.ndlam.zalo.ui.theme.Blue500
import website.ndlam.zalo.ui.theme.Blue800
import website.ndlam.zalo.ui.theme.SuperWhite
import website.ndlam.zalo.ui.theme.dimes
import website.ndlam.zalo.ui.theme.disableButton
import website.ndlam.zalo.ui.theme.onDisableButton
import website.ndlam.zalo.ui.common.textfield.viewmodel.PhoneNumberViewModel


@Composable
fun SignInScreen(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    phoneNumberViewModel: PhoneNumberViewModel = viewModel(),
    onBackPress: () -> Unit = {},
    navigateSignUpScreen: () -> Unit = {},
    onContinuePress: () -> Unit = {},
    navigateRegionCode: () -> Unit = {},
    terms: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.you_do_not_have_any_account),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = MaterialTheme.dimes.textSize.medium
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimes.sizing.xsmall))
            Text(
                text = stringResource(R.string.create_account),
                color = Blue500,
                modifier = Modifier
                    .padding(0.dp)
                    .clickable(onClick = navigateSignUpScreen),
                fontSize = MaterialTheme.dimes.textSize.medium
            )
        }
    }
) {
    val isValidPhoneNumber = phoneNumberViewModel.valid.collectAsState()

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
            text = stringResource(R.string.input_phone_number),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontSize = MaterialTheme.dimes.textSize.xxlarge,
            fontWeight = Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimes.sizing.xlarge))

        PhoneNumberTextField(
            viewModel = phoneNumberViewModel,
            navigateRegionCode = navigateRegionCode
        )

        terms()

        Spacer(modifier = Modifier.height(MaterialTheme.dimes.sizing.large))

        TextButton(
            enabled = isValidPhoneNumber.value,
            onClick = onContinuePress,
            contentPadding = PaddingValues(vertical = MaterialTheme.dimes.sizing.textButtonVerticalPadding),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Blue800,
                disabledContainerColor = MaterialTheme.colorScheme.disableButton,
                contentColor = SuperWhite,
                disabledContentColor = MaterialTheme.colorScheme.onDisableButton
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(MaterialTheme.dimes.sizing.large))
        ) {
            Text(
                text = stringResource(R.string.text_continue),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        footer()
    }
}