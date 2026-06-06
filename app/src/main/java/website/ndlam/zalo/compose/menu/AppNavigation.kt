package website.ndlam.zalo.compose.menu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import website.ndlam.zalo.compose.main.MainScreen
import website.ndlam.zalo.compose.password.PasswordScreen
import website.ndlam.zalo.compose.signin.SignInScreen
import website.ndlam.zalo.compose.signup.SignUpScreen
import website.ndlam.zalo.compose.splash.SplashScreen
import website.ndlam.zalo.compose.introduction.IntroductionScreen
import website.ndlam.zalo.compose.regioncode.RegionCodeScreen
import website.ndlam.zalo.compose.search.SearchScreen
import website.ndlam.zalo.utils.formater.PhoneNumberFormater
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
                }, onContinuePress = {
                    navController.navigate(Password::class.java.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }, navigateRegionCode = {
                    navController.navigate(RegionCode::class.java.name) {
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
                },
                onContinuePress = {

                })
        }

        composable(RegionCode::class.java.name) {
            RegionCodeScreen(
                paddingValues = paddingValues,
                onBackPress = {
                    navController.popBackStack()
                },
                onChangeRegionCode = { regionCode ->
                    phoneNumberViewModel.setRegionCode(regionCode)
                    navController.popBackStack()
                }
            )
        }

        composable(Password::class.java.name) { backStackEntry ->
            PasswordScreen(
                paddingValues = paddingValues,
                onBackPress = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                },
                phoneNumber = PhoneNumberFormater.nationalFormat(
                    phoneNumberViewModel.value.collectAsState().value.toLong(),
                    phoneNumberViewModel.regionCode.collectAsState().value.replace("+", "").toInt()
                ),
                onContinuePress = {
                    navController.navigate(Main::class.java.name) {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Main::class.java.name) { backStackEntry ->
            MainScreen(
                paddingValues = paddingValues,
                onSearchPress = {
                    navController.navigate(Search::class.java.name) {
                        launchSingleTop = true
                    }
                })
        }

        composable(Search::class.java.name) { backStackEntry ->
            SearchScreen(
                paddingValues = paddingValues,
                onBackPress = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                })
        }
    }
}