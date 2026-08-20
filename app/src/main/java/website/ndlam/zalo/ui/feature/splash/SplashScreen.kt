package website.ndlam.zalo.ui.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import website.ndlam.zalo.R
import website.ndlam.zalo.data.remote.api.ApiState
import website.ndlam.zalo.ui.common.auth.AuthViewModel
import website.ndlam.zalo.ui.common.dialog.LoadingDialog
import website.ndlam.zalo.ui.theme.Blue800
import website.ndlam.zalo.ui.theme.SuperWhite
import website.ndlam.zalo.ui.theme.dimes

@Composable
fun SplashScreen(
    navigateToMainScreen: () -> Unit = {},
    navigateToIntroductionScreen: () -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(0.dp),
    authViewModel: AuthViewModel
) {
    val loginStatus = authViewModel.loginStatus.collectAsState()

    LaunchedEffect(loginStatus.value) {
        if (loginStatus.value == null) {
            authViewModel.info()
            return@LaunchedEffect
        }

        when (loginStatus.value) {
            is ApiState.SuccessNotResponse -> navigateToMainScreen()
            is ApiState.Error -> {
                navigateToIntroductionScreen()
            }

            else -> {}
        }
    }

    LoadingDialog(loginStatus.value is ApiState.Loading<*>)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue800)
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            color = SuperWhite,
            fontWeight = Bold,
            fontSize = MaterialTheme.dimes.textSize.logo
        )
    }
}