package website.ndlam.zalo.compose.menu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import website.ndlam.zalo.compose.main.MainScreen
import website.ndlam.zalo.compose.signin.SignInScreen
import website.ndlam.zalo.compose.signup.SignUpScreen
import website.ndlam.zalo.compose.splash.SplashScreen
import website.ndlam.zalo.compose.welcome.IntroductionScreen
import website.ndlam.zalo.viewmodels.PhoneNumberViewModel

@Composable
fun AppNavigation(paddingValues: PaddingValues = PaddingValues(0.dp)) {
    val navController = rememberNavController()
    val phoneNumberViewModel = viewModel<PhoneNumberViewModel>()

    NavHost(
        navController = navController,
        startDestination = Splash::class.java.name,
    ) {
        composable(Splash::class.java.name) {
            SplashScreen(
                navigateToMainScreen = {
                    navController.navigate(Main::class.java.name) {
                        popUpTo(Splash::class.java.name) { inclusive = true }
                    }
                },
                navigateToIntroductionScreen = {
                    navController.navigate(Introduction::class.java.name) {
                        popUpTo(Splash::class.java.name) { inclusive = true }
                    }
                },
                paddingValues = paddingValues
            )
        }

        composable(Introduction::class.java.name) { backStackEntry ->
            IntroductionScreen(
                paddingValues,
                navigateToSignInScreen = {
                    navController.navigate(SignIn::class.java.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateToSignUpScreen = {
                    navController.navigate(SignUp::class.java.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(SignIn::class.java.name) { backStackEntry ->
            SignInScreen(
                paddingValues, phoneNumberViewModel,
                onBackPress = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                },
                navigateSignUpScreen = {
                    navController.navigate(SignUp::class.java.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }

        composable(SignUp::class.java.name) { backStackEntry ->
            SignUpScreen(
                paddingValues, phoneNumberViewModel,
                onBackPress = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                },
                navigateSignInScreen = {
                    navController.navigate(SignIn::class.java.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }

        composable(Main::class.java.name) { backStackEntry ->
            MainScreen(paddingValues)
        }
    }
}