package website.ndlam.zalo.compose.listroomchat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.theme.Gray200
import website.ndlam.zalo.ui.theme.Red300
import website.ndlam.zalo.ui.theme.SuperWhite
import website.ndlam.zalo.ui.theme.dimes
import website.ndlam.zalo.ui.theme.listRoomChat

data class RoomChatInfo(
    val iconUrl: String,
    val title: String,
    val lastMessage: String,
    val isPin: Boolean = false,
    val lastOnline: String,
    val totalMessageUnread: Int = 0
)

@Composable
fun RoomChatCard(
    info: RoomChatInfo
) {
    RoomChatCard(
        iconUrl = info.iconUrl,
        title = info.title,
        lastMessage = info.lastMessage,
        isPin = info.isPin,
        lastOnline = info.lastOnline,
        totalMessageUnread = info.totalMessageUnread
    )
}

@Composable
fun RoomChatCard(
    iconUrl: String,
    title: String,
    lastMessage: String,
    isPin: Boolean,
    lastOnline: String,
    totalMessageUnread: Int
) {
    val colorScheme = MaterialTheme.colorScheme.listRoomChat

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(if (isPin) colorScheme.roomChatCardSecondary else colorScheme.roomChatCardPrimary)
            .padding(start = MaterialTheme.dimes.sizing.smallAddXsmall),
    ) {
        AsyncImage(
            model = iconUrl,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(MaterialTheme.dimes.iconSize.xll)
        )

        Spacer(modifier = Modifier.width(MaterialTheme.dimes.sizing.medium))

        Row(
            modifier = Modifier
                .weight(1f)
                .height(MaterialTheme.dimes.sizing.xxlargeAddSmall)
                .drawBehind {
                    drawLine(
                        color = Gray200,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 0.5.dp.toPx()
                    )
                }
                .padding(end = MaterialTheme.dimes.sizing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = MaterialTheme.dimes.textSize.large
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = lastMessage,
                    maxLines = 1,
                    color = if (totalMessageUnread > 0) colorScheme.roomChatCardOnPrimary else colorScheme.romChatReadedMessage,
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isPin) {
                        Icon(
                            painter = painterResource(R.drawable.ic_push_pin),
                            contentDescription = null,
                            tint = colorScheme.romChatReadedMessage,
                            modifier = Modifier
                                .size(MaterialTheme.dimes.iconSize.xsm)
                                .rotate(45f)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                    Text(
                        text = lastOnline,
                        color = if (totalMessageUnread > 0) colorScheme.roomChatCardOnPrimary else colorScheme.romChatReadedMessage,
                        fontSize = MaterialTheme.dimes.textSize.small,
                    )
                }
                if (totalMessageUnread > 0) {
                    Spacer(modifier = Modifier.height(MaterialTheme.dimes.sizing.xsmall))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .width(MaterialTheme.dimes.sizing.medium)
                            .height(MaterialTheme.dimes.sizing.smallAddXsmall)
                            .clip(RoundedCornerShape(MaterialTheme.dimes.sizing.small))
                            .background(Red300),
                    ) {
                        Text(
                            text = if(totalMessageUnread > 10) "9" else  "$totalMessageUnread",
                            color = SuperWhite,
                            fontSize = MaterialTheme.dimes.textSize.xsmall,
                            lineHeight = MaterialTheme.dimes.textSize.xsmall
                        )
                    }
                }
            }
        }
    }
}