package website.ndlam.zalo.compose.signin

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import website.ndlam.zalo.R
import website.ndlam.zalo.compose.phonenumbertextfield.PhoneNumberTextField
import website.ndlam.zalo.ui.theme.Blue500
import website.ndlam.zalo.ui.theme.LocalColorScheme
import website.ndlam.zalo.ui.theme.LocalDimens
import website.ndlam.zalo.viewmodels.PhoneNumberViewModel


@Composable
fun SignInScreen(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    phoneNumberViewModel: PhoneNumberViewModel = viewModel(),
    onBackPress: () -> Unit = {},
    navigateSignUpScreen: () -> Unit = {},
    terms: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.you_do_not_have_any_account),
                color = LocalColorScheme.current.onPrimary,
                fontSize = LocalDimens.current.textSize.medium
            )
            Spacer(modifier = Modifier.width(LocalDimens.current.sizing.xsmall))
            Text(
                text = stringResource(R.string.create_account),
                color = Blue500,
                modifier = Modifier
                    .padding(0.dp)
                    .clickable(onClick = navigateSignUpScreen),
                fontSize = LocalDimens.current.textSize.medium
            )
        }
    }
) {
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
            text = stringResource(R.string.input_phone_number),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontSize = LocalDimens.current.textSize.xxlarge,
            fontWeight = Bold,
            color = LocalColorScheme.current.onPrimary
        )
        Spacer(modifier = Modifier.height(LocalDimens.current.sizing.xlarge))

        PhoneNumberTextField(viewModel = phoneNumberViewModel)

        terms()

        Spacer(modifier = Modifier.height(LocalDimens.current.sizing.large))

        TextButton(
            onClick = {}, modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(LocalDimens.current.sizing.large))
                .background(LocalColorScheme.current.signInColorScheme.disableButton)
        ) {
            Text(
                text = stringResource(R.string.text_continue),
                color = LocalColorScheme.current.signInColorScheme.onDisableButton
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        footer()
    }
}


//@Preview
//@Composable
//fun PreviewScreen() {
//    SignInScreen()
//}