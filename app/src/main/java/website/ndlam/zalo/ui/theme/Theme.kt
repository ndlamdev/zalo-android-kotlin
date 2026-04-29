package website.ndlam.zalo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


/*
private val DarkColorScheme = darkColorScheme(
    primary = Black800,
    onPrimary = White,

    secondary = Color(0xFF3D3D3D),
    onSecondary = Color(0xFF005ADF),

    tertiary = Pink80,
    onTertiary = Color(0xFF0344A8),

    background = Color(0xFFF3F4F6),
    surface = Color(0xFF171717),
)

private val LightColorScheme = lightColorScheme(
    primary = SuperWhite,
    onPrimary = Black900,

    secondary = Color(0xFFE6E6E8),
    onSecondary = Color(0xFF005ADF),

    tertiary = Gray200,
    onTertiary = Color(0xFF8FBFFF),


    background = Color(0xFFF3F4F6),
    surface = SuperWhite,

    */
/* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    *//*

)
*/

val LocalAppDimens = staticCompositionLocalOf { Dimens() }
val LocalColorScheme = staticCompositionLocalOf { ColorScheme() }

@Composable
fun ZolaApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(
        LocalAppDimens provides LocalAppDimens.current,
        LocalColorScheme provides colorScheme
    ) {
//        MaterialTheme(
//            colorScheme = colorScheme,
//            typography = Typography,
//            content = content
//        )
        content()
    }
}