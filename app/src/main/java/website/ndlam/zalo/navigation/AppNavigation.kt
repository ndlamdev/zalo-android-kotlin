package website.ndlam.zalo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import website.ndlam.zalo.BuildConfig
import website.ndlam.zalo.core.util.viewmodel.initViewModelWithDependencies
import website.ndlam.zalo.data.repository.AuthRepositoryImpl
import website.ndlam.zalo.data.repository.TokenMangerImpl
import website.ndlam.zalo.domain.repository.ITokenManager
import website.ndlam.zalo.network.RetrofitClientSecured
import website.ndlam.zalo.ui.common.auth.AuthViewModel
import website.ndlam.zalo.ui.common.textfield.viewmodel.PhoneNumberViewModel
import website.ndlam.zalo.ui.feature.introduction.IntroductionScreen
import website.ndlam.zalo.ui.feature.main.MainScreen
import website.ndlam.zalo.ui.feature.main.MainViewModel
import website.ndlam.zalo.ui.feature.main.StompMessageViewModel
import website.ndlam.zalo.ui.feature.password.PasswordScreen
import website.ndlam.zalo.ui.feature.regioncode.RegionCodeScreen
import website.ndlam.zalo.ui.feature.search.SearchScreen
import website.ndlam.zalo.ui.feature.signin.SignInScreen
import website.ndlam.zalo.ui.feature.signup.SignUpScreen
import website.ndlam.zalo.ui.feature.splash.SplashScreen

@Composable
fun AppNavigation(paddingValues: PaddingValues = PaddingValues(0.dp)) {
    val context = LocalContext.current
    val authRepository = AuthRepositoryImpl(context)
    val tokenManager = TokenMangerImpl(authRepository)
    val navController = rememberNavController()
    val phoneNumberViewModel = viewModel<PhoneNumberViewModel>()
    val phoneNumber = phoneNumberViewModel.value.collectAsState()
    val regionCode = phoneNumberViewModel.regionCode.collectAsState()
    val mainViewModel = viewModel<MainViewModel>()
    val authViewModel = initViewModelWithDependencies<AuthViewModel>(tokenManager)
    val stompMessageViewModel = initViewModelWithDependencies<StompMessageViewModel>(tokenManager)

    val onLoginSuccess: () -> Unit = {
        mainViewModel.getUserInfo()
        stompMessageViewModel.connect(BuildConfig.API_CHATWS_URL)

        navController.navigate(Main::class.java.name) {
            popUpTo(0) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    LaunchedEffect(Unit) {
        RetrofitClientSecured.tokenManager = tokenManager
    }

    NavHost(
        navController = navController,
        startDestination = Splash::class.java.name,
    ) {
        composable(Splash::class.java.name) {
            SplashScreen(
                navigateToMainScreen = onLoginSuccess,
                navigateToIntroductionScreen = {
                    navController.navigate(Introduction::class.java.name) {
                        popUpTo(Splash::class.java.name) {
                            inclusive = true
                        }
                    }
                },
                paddingValues = paddingValues,
                authViewModel = authViewModel
            )
        }

        composable(Introduction::class.java.name) {
            IntroductionScreen(
                paddingValues,
                navigateToSignInScreen = {
                    authViewModel.clearLoginStatus()
                    navController.navigate(SignIn::class.java.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateToSignUpScreen = {
                    authViewModel.clearLoginStatus()
                    navController.navigate(SignUp::class.java.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(SignIn::class.java.name) {
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
                },
                onContinuePress = {
                    navController.navigate(Password::class.java.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateRegionCode = {
                    navController.navigate(RegionCode::class.java.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }

        composable(SignUp::class.java.name) {
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

                },
                navigateRegionCode = {
                    navController.navigate(RegionCode::class.java.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
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

        composable(Password::class.java.name) {
            PasswordScreen(
                paddingValues = paddingValues,
                onBackPress = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                },
                swissNumber = phoneNumber.value,
                regionCode = regionCode.value,
                onLoginSuccess = onLoginSuccess,
                authViewModel = authViewModel
            )
        }

        composable(Main::class.java.name) {
            MainScreen(
                paddingValues = paddingValues,
                stompMessageViewModel = stompMessageViewModel,
                onSearchPress = {
                    navController.navigate(Search::class.java.name) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Search::class.java.name) {
            SearchScreen(
                paddingValues = paddingValues,
                onBackPress = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}