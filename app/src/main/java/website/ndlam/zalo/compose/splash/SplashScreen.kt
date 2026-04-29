package website.ndlam.zalo.compose.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.theme.Blue800
import website.ndlam.zalo.ui.theme.LocalAppDimens
import website.ndlam.zalo.ui.theme.SuperWhite
import website.ndlam.zalo.utils.enums.ApiCallingStatus
import website.ndlam.zalo.viewmodels.SplashViewModel

@Composable
fun SplashScreen(onNavigate: () -> Unit = {}, viewModel: SplashViewModel = viewModel()) {
    val loadUserInfoState = viewModel.loadInfoUserState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadInfo()
    }

    when (loadUserInfoState.value) {
        is ApiCallingStatus.Loading -> {}
        is ApiCallingStatus.Success -> onNavigate()
        is ApiCallingStatus.Error -> {}
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue800),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            color = SuperWhite,
            fontWeight = Bold,
            fontSize = LocalAppDimens.current.textSize.h1 * 2.5
        )
    }
}