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
import kotlinx.coroutines.flow.firstOrNull
import website.ndlam.zalo.R
import website.ndlam.zalo.core.util.enums.ApiCallingStatus
import website.ndlam.zalo.data.repository.AuthTokenRepositoryImpl
import website.ndlam.zalo.domain.repository.IAuthTokenRepository
import website.ndlam.zalo.ui.theme.Blue800
import website.ndlam.zalo.ui.theme.SuperWhite
import website.ndlam.zalo.ui.theme.dimes
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(
    navigateToMainScreen: () -> Unit = {},
    navigateToIntroductionScreen: () -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(0.dp),
    authTokenRepository: IAuthTokenRepository
) {
    LaunchedEffect(Unit) {
        val isSignIn = authTokenRepository.isSignIn().firstOrNull()

        if (isSignIn == true) {
            navigateToMainScreen()
        } else {
            delay(500.milliseconds)

            navigateToIntroductionScreen()
        }
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
            fontSize = MaterialTheme.dimes.textSize.logo
        )
    }
}