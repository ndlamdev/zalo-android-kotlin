package website.ndlam.zalo.compose.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import website.ndlam.zalo.R
import website.ndlam.zalo.compose.signin.SignInScreen
import website.ndlam.zalo.ui.theme.Blue500
import website.ndlam.zalo.ui.theme.Blue800
import website.ndlam.zalo.ui.theme.LocalColorScheme
import website.ndlam.zalo.ui.theme.LocalDimens
import website.ndlam.zalo.ui.theme.SuperWhite
import website.ndlam.zalo.viewmodels.PhoneNumberViewModel


@Composable
fun SignUpScreen(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    phoneNumberViewModel: PhoneNumberViewModel = viewModel(),
    onBackPress: () -> Unit = {},
    navigateSignInScreen: () -> Unit = {},
    onContinuePress: () -> Unit = {},
) {
    SignInScreen(
        paddingValues = paddingValues,
        phoneNumberViewModel = phoneNumberViewModel,
        onBackPress = onBackPress,
        onContinuePress = onContinuePress,
        terms = {
            TermCheckBox(
                title = stringResource(R.string.i_agree_with_these),
                linkTitle = stringResource(R.string.terms_of_use_zola),
                modifier = Modifier.padding(vertical = LocalDimens.current.sizing.small + LocalDimens.current.sizing.xsmall)
            )
            TermCheckBox(
                checked = false,
                title = stringResource(R.string.i_agree_with),
                linkTitle = stringResource(R.string.zola_is_Social_network_terms)
            )
            Spacer(modifier = Modifier.height(LocalDimens.current.sizing.medium))
        },
        footer = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.you_have_account),
                    color = LocalColorScheme.current.onPrimary,
                    fontSize = LocalDimens.current.textSize.medium
                )
                Spacer(modifier = Modifier.width(LocalDimens.current.sizing.xsmall))
                Text(
                    text = stringResource(R.string.login_now),
                    color = Blue500,
                    modifier = Modifier
                        .padding(0.dp)
                        .clickable(onClick = navigateSignInScreen),
                    fontSize = LocalDimens.current.textSize.medium
                )
            }
        }
    )
}

@Composable
fun TermCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    title: String = "",
    linkTitle: String = ""
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
            Checkbox(
                onCheckedChange = onCheckedChange,
                checked = checked,
                modifier = Modifier.clip(RoundedCornerShape(LocalDimens.current.sizing.medium)),
                colors = CheckboxDefaults.colors(
                    checkedColor = Blue800,
                    checkmarkColor = SuperWhite
                )
            )
        }
        Text(
            text = title,
            color = LocalColorScheme.current.onPrimary,
            modifier = Modifier.padding(
                LocalDimens.current.sizing.small,
                0.dp,
                LocalDimens.current.sizing.xsmall,
                0.dp
            ), fontSize = LocalDimens.current.textSize.medium
        )
        Text(text = linkTitle, color = Blue500, fontSize = LocalDimens.current.textSize.medium)
    }
}
