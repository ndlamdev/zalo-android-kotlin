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

// default for light theme
data class IntroductionColorScheme(
    val secondaryButton: Color = Gray200,
    val onSecondaryButton: Color = Black850,
    val description: Color = Gray550,
    val tintICon: Color = Blue100
)


data class RegionCodeScreenColorScheme(
    val groupName: Color = Blue150
)

data class HeaderBarColorScheme(val background: Brush = HeaderGradient)

data class MenuBarColorScheme(val background: Color = LightBlue500)

data class HeaderColorScheme(
    val background: Brush = HeaderGradient,
    val inputBackground: Color = SuperWhite,
)

data class MessageSearchItemColorScheme(
    var border: Color = Gray300
)


val ColorScheme.disableButton: Color
    @Composable
    get() = if (isSystemInDarkTheme()) BlackGray else Gray250


val ColorScheme.onDisableButton: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Gray710 else Gray450

val ColorScheme.introductionColorScheme: IntroductionColorScheme
    @Composable
    get() = if (isSystemInDarkTheme()) IntroductionColorScheme(
        secondaryButton = Gray800,
        onSecondaryButton = SuperWhite,
        description = Gray600,
        tintICon = Gray300
    ) else IntroductionColorScheme()


val ColorScheme.phoneNumberTextFieldColorScheme: PhoneNumberTextFieldColorScheme
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

val ColorScheme.passwordScreenColorScheme: PasswordScreenColorScheme
    @Composable
    get() = if (isSystemInDarkTheme())
        PasswordScreenColorScheme(
            forgetPassword = Blue350
        )
    else PasswordScreenColorScheme()


val ColorScheme.localTextFieldColorScheme: LocalTextFieldColorScheme
    @Composable
    get() = if (isSystemInDarkTheme())
        LocalTextFieldColorScheme(
            borderFocus = Blue700,
            border = Gray690,

            cursor = SolidColor(Blue700)
        )
    else LocalTextFieldColorScheme()


val ColorScheme.regionCodeScreenColorScheme: RegionCodeScreenColorScheme
    @Composable
    get() = if (isSystemInDarkTheme())
        RegionCodeScreenColorScheme(
            groupName = Blue900
        )
    else RegionCodeScreenColorScheme()

val ColorScheme.headerBarColorScheme: HeaderBarColorScheme
    @Composable
    get() = if (isSystemInDarkTheme())
        HeaderBarColorScheme(
            background = SolidColor(Gray900)
        )
    else HeaderBarColorScheme()

val ColorScheme.menuBarColorScheme: MenuBarColorScheme
    @Composable
    get() = if (isSystemInDarkTheme())
        MenuBarColorScheme(
            background = Gray820
        )
    else MenuBarColorScheme()

val ColorScheme.headerColorScheme: HeaderColorScheme
    @Composable
    get() = if (isSystemInDarkTheme())
        HeaderColorScheme(
            background = SolidColor(Black50),
            inputBackground = Black900
        )
    else HeaderColorScheme()

val ColorScheme.messageSearchItemColorScheme: MessageSearchItemColorScheme
    @Composable
    get() = if (isSystemInDarkTheme())
        MessageSearchItemColorScheme(
            border = BlackGray
        )
    else MessageSearchItemColorScheme()