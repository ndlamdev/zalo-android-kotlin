package website.ndlam.zalo.compose.welcome

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times
import website.ndlam.zalo.ui.theme.DotColor
import website.ndlam.zalo.ui.theme.DotSelectedColor

@Composable
fun DotsCompose(
    modifier: Modifier = Modifier,
    amount: Int,
    size: Dp,
    dotColor: Color = DotColor,
    dotSelectedColor: Color = DotSelectedColor,
    dotSelected: Int = 0,
    spacing: Dp = size
) {
    val positionDotSelected = animateDpAsState(
        targetValue = dotSelected * (spacing + size),
        label = "dotAnimation"
    )


    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Box {
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing)
            ) {
                repeat(amount) {
                    Dot(size = size, color = dotColor)
                }
            }

            Dot(
                size = size,
                color = dotSelectedColor,
                modifier = Modifier
                    .graphicsLayer {
                        translationX = positionDotSelected.value.toPx()
                    }
                    .clip(CircleShape)
            )
        }
    }
}


@Composable
fun Dot(modifier: Modifier = Modifier, size: Dp, color: Color) {
    Canvas(modifier = modifier.size(size)) {
        drawCircle(color = color)
    }
}