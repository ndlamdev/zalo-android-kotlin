package website.ndlam.zalo.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor

data class PhoneNumberTextFieldColorScheme(
    val borderFocus: Color = Blue810,
    val iconFocus: Color = Blue810,
    val borderCountryCodeFocus: Color = Blue75,
    val surfaceFocus: Color = LightBlue500,

    val border: Color = Gray455,
    val icon: Color = Black900,
    val borderCountryCode: Color = Gray300,
    val surface: Color = Blue50,

    val cursor: Brush = SolidColor(Blue810)
)

data class PasswordTextFieldColorScheme(
    val borderFocus: Color = Blue810,
    val border: Color = Gray455,

    val cursor: Brush = SolidColor(Blue810)
)

data class PasswordScreenColorScheme(
    val forgetPassword: Color = Blue850,
)

// default for light theme
data class IntroductionColorScheme(
    val secondaryButton: Color = Gray200,
    val onSecondaryButton: Color = Black850,
    val description: Color = Gray550,
    val tintICon: Color = Blue100
)

// default for light theme
data class ColorScheme(
    val primary: Color = Gray50,
    val onPrimary: Color = Black50,
    val surface: Color = SuperWhite,
    val disableButton: Color = Gray250,
    val onDisableButton: Color = Gray450,
    val introductionColorScheme: IntroductionColorScheme = IntroductionColorScheme(),
    val phoneNumberTextFieldColorScheme: PhoneNumberTextFieldColorScheme = PhoneNumberTextFieldColorScheme(),
    val passwordTextFieldColorScheme: PasswordTextFieldColorScheme = PasswordTextFieldColorScheme(),
    val passwordScreenColorScheme: PasswordScreenColorScheme = PasswordScreenColorScheme()
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
    passwordTextFieldColorScheme = PasswordTextFieldColorScheme(
        borderFocus = Blue700,
        border = Gray690,

        cursor = SolidColor(Blue700)
    ),
    passwordScreenColorScheme = PasswordScreenColorScheme(
        forgetPassword = Blue350
    )
)

val LightColorScheme = ColorScheme()