package website.ndlam.zalo.ui.theme

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


// default for light theme
data class ColorScheme(
    val primary: Color = Gray50,
    val onPrimary: Color = Black50,
    val surface: Color = SuperWhite,
    val disableButton: Color = Gray250,
    val onDisableButton: Color = Gray450,
    val introductionColorScheme: IntroductionColorScheme = IntroductionColorScheme(),
    val phoneNumberTextFieldColorScheme: PhoneNumberTextFieldColorScheme = PhoneNumberTextFieldColorScheme(),
    val passwordScreenColorScheme: PasswordScreenColorScheme = PasswordScreenColorScheme(),
    val localTextFieldColorScheme: LocalTextFieldColorScheme = LocalTextFieldColorScheme(),
    val regionCodeScreenColorScheme: RegionCodeScreenColorScheme = RegionCodeScreenColorScheme(),
    val headerBarColorScheme: HeaderBarColorScheme = HeaderBarColorScheme(),
    val menuBarColorScheme: MenuBarColorScheme = MenuBarColorScheme(),
)

val DarkColorScheme = ColorScheme(
    primary = Black900,
    onPrimary = SuperWhite,
    surface = Black300,
    introductionColorScheme = IntroductionColorScheme(
        secondaryButton = Gray800,
        onSecondaryButton = SuperWhite,
        description = Gray600,
        tintICon = Gray300
    ),
    disableButton = BlackGray,
    onDisableButton = Gray710,
    phoneNumberTextFieldColorScheme = PhoneNumberTextFieldColorScheme(
        borderFocus = Blue700,
        iconFocus = Blue700,
        borderCountryCodeFocus = DarkBlue500,
        surfaceFocus = DarkBlue900,

        border = Gray690,
        icon = SuperWhite,
        borderCountryCode = Gray700,
        surface = Gray800,

        cursor = SolidColor(Blue700)
    ),
    localTextFieldColorScheme = LocalTextFieldColorScheme(
        borderFocus = Blue700,
        border = Gray690,

        cursor = SolidColor(Blue700)
    ),
    passwordScreenColorScheme = PasswordScreenColorScheme(
        forgetPassword = Blue350
    ),
    regionCodeScreenColorScheme = RegionCodeScreenColorScheme(
        groupName = Blue900
    ),
    headerBarColorScheme = HeaderBarColorScheme(
        background = SolidColor(Gray900)
    ),
    menuBarColorScheme = MenuBarColorScheme(
        background = Gray820
    )
)

val LightColorScheme = ColorScheme()