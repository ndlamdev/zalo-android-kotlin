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
import androidx.navigation.navArgument
import website.ndlam.zalo.BuildConfig
import website.ndlam.zalo.core.util.converter.GsonConverter
import website.ndlam.zalo.core.util.enums.ScreenOnMainScreen
import website.ndlam.zalo.core.util.viewmodel.initViewModelWithDependencies
import website.ndlam.zalo.data.remote.api.ConversationDto
import website.ndlam.zalo.data.repository.AuthRepositoryImpl
import website.ndlam.zalo.data.repository.TokenMangerImpl
import website.ndlam.zalo.navigation.type.LocalNavType
import website.ndlam.zalo.network.RetrofitClientSecured
import website.ndlam.zalo.ui.common.auth.AuthViewModel
import website.ndlam.zalo.ui.common.textfield.viewmodel.PhoneNumberViewModel
import website.ndlam.zalo.ui.feature.introduction.IntroductionScreen
import website.ndlam.zalo.ui.feature.main.MainScreen
import website.ndlam.zalo.ui.feature.main.MainViewModel
import website.ndlam.zalo.ui.feature.main.StompMessageViewModel
import website.ndlam.zalo.ui.feature.password.PasswordScreen
import website.ndlam.zalo.ui.feature.regioncode.RegionCodeScreen
import website.ndlam.zalo.ui.feature.roomchat.RoomChatScreen
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

        navController.navigate(NavDestinations.Main.name) {
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
        startDestination = NavDestinations.Splash.name,
    ) {
        composable(NavDestinations.Splash.name) {
            SplashScreen(
                navigateToMainScreen = onLoginSuccess,
                navigateToIntroductionScreen = {
                    navController.navigate(NavDestinations.Introduction.name) {
                        popUpTo(NavDestinations.Splash.name) {
                            inclusive = true
                        }
                    }
                },
                paddingValues = paddingValues,
                authViewModel = authViewModel
            )
        }

        composable(NavDestinations.Introduction.name) {
            IntroductionScreen(
                paddingValues,
                navigateToSignInScreen = {
                    authViewModel.clearLoginStatus()
                    navController.navigate(NavDestinations.SignIn.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateToSignUpScreen = {
                    authViewModel.clearLoginStatus()
                    navController.navigate(NavDestinations.SignUp.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(NavDestinations.SignIn.name) {
            SignInScreen(
                paddingValues, phoneNumberViewModel,
                onBackPress = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                },
                navigateSignUpScreen = {
                    navController.navigate(NavDestinations.SignUp.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onContinuePress = {
                    navController.navigate(NavDestinations.Password.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateRegionCode = {
                    navController.navigate(NavDestinations.RegionCode.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }

        composable(NavDestinations.SignUp.name) {
            SignUpScreen(
                paddingValues, phoneNumberViewModel,
                onBackPress = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                },
                navigateSignInScreen = {
                    navController.navigate(NavDestinations.SignIn.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onContinuePress = {

                },
                navigateRegionCode = {
                    navController.navigate(NavDestinations.RegionCode.name) {
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }

        composable(NavDestinations.RegionCode.name) {
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

        composable(NavDestinations.Password.name) {
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

        composable(NavDestinations.Main.name) {
            MainScreen(
                paddingValues = paddingValues,
                navController = navController,
                stompMessageViewModel = stompMessageViewModel
            )
        }

        composable(NavDestinations.Search.name) {
            SearchScreen(
                paddingValues = paddingValues,
                onBackPress = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            )
        }

        composable(
            "${NavDestinations.ROOMCHAT.name}/{data}",
            arguments = listOf(navArgument("data") {
                type = LocalNavType(true, ConversationDto::class.java)
            })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("data") ?: return@composable
            val data = GsonConverter.gson.fromJson(
                json,
                ConversationDto::class.java
            )
            RoomChatScreen(data, onBack = {
                if (navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                }
            })
        }
    }
}