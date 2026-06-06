package website.ndlam.zalo.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Dimens(
    val textSize: TextSize = TextSize(),
    val sizing: Sizing = Sizing(),
    val iconSize: IconSize = IconSize()
)

data class TextSize(
    val xsmall: TextUnit = 9.sp,
    val small: TextUnit = 11.sp,
    val medium: TextUnit = 15.sp,
    val large: TextUnit = 17.sp,
    val xlarge: TextUnit = 19.sp,
    val xxlarge: TextUnit = 23.sp,
    val h2: TextUnit = 29.sp,
    val h1: TextUnit = 37.sp,
    val caption: TextUnit = 11.sp,
    val button: TextUnit = 15.sp,
)

data class Sizing(
    // Global Spacing & Sizing
    val xsmall: Dp = 5.dp,
    val small: Dp = 10.dp,
    val smallAddXSmall: Dp = 15.dp,
    val medium: Dp = 20.dp,
    val mediumAddXSmall: Dp = 25.dp,
    val large: Dp = 30.dp,
    val largeAddXSmall: Dp = 35.dp,
    val xlarge: Dp = 40.dp,
    val xlargeAddXSmall: Dp = 45.dp,
    val xxlarge: Dp = 50.dp,
    val textButtonVerticalPadding: Dp = 16.dp
)

data class IconSize(
    val xll6: Dp = 70.dp,
    val xll: Dp = 60.dp,
    val xl: Dp = 50.dp,
    val lg: Dp = 40.dp,
    val md: Dp = 30.dp,
    val sm: Dp = 20.dp,
    val xsm: Dp = 15.dp,
)

//data class Other(
//    // Specific UI Components
//    val gapItemInLinearLayout: Dp = 5.dp,
//
//    val radiusComponentSearch: Dp = 5.dp,
//    val sizeAvatarRoomChat: Dp = 50.dp,
//    val itemSpacing: Dp = 10.dp,
//
//    val dp7: Dp = 7.dp,
//    val dp2: Dp = 2.dp
//)

