package website.ndlam.zalo.ui.feature.search

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
import androidx.compose.material3.MaterialTheme
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
import website.ndlam.zalo.ui.theme.dimes
import website.ndlam.zalo.ui.theme.searchScreen


@Composable
fun MessageItem(
    message: SearchMessage
) {
    val borderColor = MaterialTheme.colorScheme.searchScreen.messageBorder

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = MaterialTheme.dimes.sizing.small,
                start = MaterialTheme.dimes.sizing.smallAddXsmall,
            ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimes.sizing.small)
    ) {
        Image(
            painter = ColorPainter(Color.Gray),
            contentDescription = null,
            modifier = Modifier
                .size(MaterialTheme.dimes.iconSize.xl)
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
                    bottom = MaterialTheme.dimes.sizing.smallAddXsmall,
                    end = MaterialTheme.dimes.sizing.smallAddXsmall
                )
        ) {
            Row {
                Text(
                    text = message.senderName,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = MaterialTheme.dimes.textSize.large,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = message.date,
                    color = Color.Gray,
                    fontSize = MaterialTheme.dimes.textSize.small,
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.dimes.sizing.medium))

            VisitCard(
                info = VisitCardInfo(
                    name = message.quotedName,
                    phoneNumber = message.quotedPhone
                )
            )
        }
    }
}