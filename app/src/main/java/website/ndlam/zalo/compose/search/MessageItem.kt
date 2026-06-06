package website.ndlam.zalo.compose.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import website.ndlam.zalo.ui.theme.LocalColorScheme
import website.ndlam.zalo.ui.theme.LocalDimens


@Composable
fun MessageItem(
    message: SearchMessage
) {
    val borderColor = LocalColorScheme.current.messageSearchItemColorScheme.border

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalColorScheme.current.surface)
            .padding(
                top = LocalDimens.current.sizing.small,
                start = LocalDimens.current.sizing.smallAddXSmall,
            ),
        horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.sizing.small)
    ) {
        Image(
            painter = ColorPainter(Color.Gray),
            contentDescription = null,
            modifier = Modifier
                .size(LocalDimens.current.iconSize.xl)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .drawBehind {
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                .padding(
                    bottom = LocalDimens.current.sizing.smallAddXSmall,
                    end = LocalDimens.current.sizing.smallAddXSmall
                )
        ) {
            Row {
                Text(
                    text = message.senderName,
                    color = LocalColorScheme.current.onPrimary,
                    fontSize = LocalDimens.current.textSize.large,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = message.date,
                    color = Color.Gray,
                    fontSize = LocalDimens.current.textSize.small,
                )
            }

            Spacer(modifier = Modifier.height(LocalDimens.current.sizing.medium))

            VisitCard(
                info = VisitCardInfo(
                    name = message.quotedName,
                    phoneNumber = message.quotedPhone
                )
            )
        }
    }
}