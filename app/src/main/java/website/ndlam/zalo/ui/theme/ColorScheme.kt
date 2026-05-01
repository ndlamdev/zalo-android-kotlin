package website.ndlam.zalo.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor

data class PhoneNumberTextFieldColorScheme(
    val border: Color = Blue810,
    val icon: Color = Blue810,
    val borderText: Color = Blue75,
    val surface: Color = LightBlue500,
    val cursor: Brush = SolidColor(Blue100)
) {

}

data class SignInColorScheme(
    val disableButton: Color = Gray250,
    val onDisableButton: Color = Gray450,
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
    val introductionColorScheme: IntroductionColorScheme = IntroductionColorScheme(),
    val signInColorScheme: SignInColorScheme = SignInColorScheme(),
    val phoneNumberTextFieldColorScheme: PhoneNumberTextFieldColorScheme = PhoneNumberTextFieldColorScheme()
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
    signInColorScheme = SignInColorScheme(
        disableButton = BlackGray,
        onDisableButton = Gray710
    ),
    phoneNumberTextFieldColorScheme = PhoneNumberTextFieldColorScheme(
        border = Blue700,
        icon = Blue700,
        borderText = DarkBlue500,
        surface = DarkBlue900,
        cursor = SolidColor(Blue600)
    )
)

val LightColorScheme = ColorScheme()