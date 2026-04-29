package website.ndlam.zalo.compose.menu

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import website.ndlam.zalo.compose.splash.SplashScreen
import website.ndlam.zalo.compose.welcome.IntroductionScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Splash,
        modifier = modifier
    ) {
        composable<Splash> {
            SplashScreen(onNavigate = {
                navController.navigate(Introduction) {
                    popUpTo<Splash> { inclusive = true }
                }
            })
        }

        composable<Introduction> { backStackEntry ->
            IntroductionScreen()
        }
    }
}