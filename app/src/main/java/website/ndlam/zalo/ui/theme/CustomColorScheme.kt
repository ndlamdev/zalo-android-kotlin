package website.ndlam.zalo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor

data class LocalTextFieldColorScheme(
    val borderFocus: Color = Blue850,
    val border: Color = Gray455,

    val cursor: Brush = SolidColor(Blue850)
)

data class PhoneNumberTextFieldColorScheme(
    val borderFocus: Color = Blue850,
    val iconFocus: Color = Blue850,
    val borderCountryCodeFocus: Color = Blue75,
    val surfaceFocus: Color = LightBlue500,

    val border: Color = Gray455,
    val icon: Color = Black900,
    val borderCountryCode: Color = Gray300,
    val surface: Color = Blue50,

    val cursor: Brush = SolidColor(Blue850)
)

data class PasswordScreenColorScheme(
    val forgetPassword: Color = Blue840,
)

data class IntroductionScreenColorScheme(
    val secondaryButton: Color = Gray200,
    val onSecondaryButton: Color = Black850,
    val description: Color = Gray550,
    val tintICon: Color = Blue100
)


data class RegionCodeScreenColorScheme(
    val groupName: Color = Blue150
)

data class MainScreenColorScheme(
    val headerBarBackground: Brush = HeaderGradient,
    val headerBarTextSearch: Color = Blue350,
    val menuBarBackground: Color = LightBlue500
)


data class SearchScreenColorScheme(
    val topBarBackground: Brush = HeaderGradient,
    val topBarInputBackground: Color = SuperWhite,
    val messageBorder: Color = Gray300,
    val filterChipBackground: Color = Gray100,
)


data class ListConversationColorScheme(
    val cardPrimary: Color = SuperWhite,
    val cardOnPrimary: Color = Black900,
    val cardSecondary: Color = White,
    val readMessage: Color = Gray450,
)

val ColorScheme.disableButton: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Gray840 else Gray250


val ColorScheme.onDisableButton: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Gray710 else Gray450

val ColorScheme.introductionScreen: IntroductionScreenColorScheme
    @Composable
    get() = if (isSystemInDarkTheme())
        IntroductionScreenColorScheme(
            secondaryButton = Gray800,
            onSecondaryButton = SuperWhite,
            description = Gray600,
            tintICon = Gray300
        )
    else IntroductionScreenColorScheme()


val ColorScheme.phoneNumberTextField: PhoneNumberTextFieldColorScheme
    @Composable
    get() = if (isSystemInDarkTheme())
        PhoneNumberTextFieldColorScheme(
            borderFocus = Blue700,
            iconFocus = Blue700,
            borderCountryCodeFocus = DarkBlue500,
            surfaceFocus = DarkBlue900,

            border = Gray690,
            icon = SuperWhite,
            borderCountryCode = Gray700,
            surface = Gray800,

            )
    else PhoneNumberTextFieldColorScheme()

val ColorScheme.passwordScreen: PasswordScreenColorScheme
    @Composable
    get() = if (isSystemInDarkTheme())
        PasswordScreenColorScheme(
            forgetPassword = Blue350
        )
    else PasswordScreenColorScheme()


val ColorScheme.localTextField: LocalTextFieldColorScheme
    @Composable
    get() = if (isSystemInDarkTheme())
        LocalTextFieldColorScheme(
            borderFocus = Blue700,
            border = Gray690,

            cursor = SolidColor(Blue700)
        )
    else LocalTextFieldColorScheme()


val ColorScheme.regionCodeScreen: RegionCodeScreenColorScheme
    @Composable
    get() = if (isSystemInDarkTheme())
        RegionCodeScreenColorScheme(
            groupName = Blue900
        )
    else RegionCodeScreenColorScheme()

val ColorScheme.searchScreen: SearchScreenColorScheme
    @Composable
    get() = if (isSystemInDarkTheme())
        SearchScreenColorScheme(
            topBarBackground = SolidColor(Black50),
            topBarInputBackground = Black900,
            messageBorder = Gray840,
            filterChipBackground = Gray920
        )
    else SearchScreenColorScheme()

val ColorScheme.mainScreen: MainScreenColorScheme
    @Composable
    get() = if (isSystemInDarkTheme())
        MainScreenColorScheme(
            headerBarBackground = SolidColor(Gray900),
            headerBarTextSearch = Gray600,
            menuBarBackground = Gray820,
        )
    else MainScreenColorScheme()


val ColorScheme.listRoomChat: ListConversationColorScheme
    @Composable
    get() = if (isSystemInDarkTheme())
        ListConversationColorScheme(
            cardPrimary = Gray960,
            cardOnPrimary = SuperWhite,
            cardSecondary = Gray920,
            readMessage = Gray710
        )
    else ListConversationColorScheme()