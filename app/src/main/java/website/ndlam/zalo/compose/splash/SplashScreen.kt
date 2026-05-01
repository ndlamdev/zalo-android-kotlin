package website.ndlam.zalo.compose.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import website.ndlam.zalo.R
import website.ndlam.zalo.repositories.AuthTokenRepository
//import website.ndlam.zalo.repositories.AuthTokenRepository
import website.ndlam.zalo.ui.theme.Blue800
import website.ndlam.zalo.ui.theme.LocalDimens
import website.ndlam.zalo.ui.theme.SuperWhite
import website.ndlam.zalo.utils.enums.ApiCallingStatus
import website.ndlam.zalo.viewmodels.SplashViewModel

@Composable
fun SplashScreen(
    navigateToMainScreen: () -> Unit = {},
    navigateToIntroductionScreen: () -> Unit = {},
    viewModel: SplashViewModel = viewModel(),
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    val loadUserInfoState = viewModel.loadInfoUserState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val authTokenDataStore = remember(context) {
        AuthTokenRepository(context)
    }
    val isSignIn =
        authTokenDataStore.isSignIn().collectAsState(false, coroutineScope.coroutineContext)

    LaunchedEffect(Unit) {
        delay(500)
        if (isSignIn.value) {
            viewModel.loadInfo()
            return@LaunchedEffect
        }

        navigateToIntroductionScreen()
    }

    when (loadUserInfoState.value) {
        is ApiCallingStatus.Loading -> {}
        is ApiCallingStatus.Success -> navigateToMainScreen()
        is ApiCallingStatus.Error -> {}
    }


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
            fontSize = LocalDimens.current.textSize.h1 * 2.5
        )
    }
}