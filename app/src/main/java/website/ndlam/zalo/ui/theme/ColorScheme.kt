package website.ndlam.zalo.ui.theme

import androidx.compose.ui.graphics.Color

// default for light theme
data class IntroductionColorScheme(
    val secondaryButton: Color = Gray200,
    val onSecondaryButton: Color = Black850,
    val description: Color = Gray550,
    val tintICon: Color = Blue100
)

// default for light theme
data class ColorScheme(
    val surface: Color = SuperWhite,
    val onPrimary: Color = Black50,
    val introductionColorScheme: IntroductionColorScheme = IntroductionColorScheme()
)

val DarkColorScheme = ColorScheme(
    surface = Black300,
    onPrimary = SuperWhite,
    introductionColorScheme = IntroductionColorScheme(
        secondaryButton = Gray800,
        onSecondaryButton = SuperWhite,
        description = Gray600,
        tintICon = Gray300
    )
)

val LightColorScheme = ColorScheme()