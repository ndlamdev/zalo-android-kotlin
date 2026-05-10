package website.ndlam.zalo.compose.rootchatcard

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.theme.Gray200
import website.ndlam.zalo.ui.theme.LocalColorScheme
import website.ndlam.zalo.ui.theme.LocalDimens
import website.ndlam.zalo.ui.theme.Red400
import website.ndlam.zalo.ui.theme.SuperWhite

@Composable
fun RoomChatCard(
    iconUrl: String,
    title: String,
    lastMessage: String,
    isPin: Boolean,
    lastOnline: String,
    totalMessageUnred: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = LocalDimens.current.sizing.medium),
    ) {
        AsyncImage(
            model = iconUrl,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(LocalDimens.current.iconSize.Xll)
        )

        Spacer(modifier = Modifier.width(LocalDimens.current.sizing.medium))

        Row(
            modifier = Modifier
                .weight(1f)
                .height(LocalDimens.current.sizing.xxlarge + LocalDimens.current.sizing.small)
                .drawBehind {
                    drawLine(
                        color = Gray200,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 0.5.dp.toPx()
                    )
                }
                .padding(end = LocalDimens.current.sizing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    maxLines = 1,
                    color = LocalColorScheme.current.onPrimary,
                    fontSize = LocalDimens.current.textSize.large
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = lastMessage,
                    maxLines = 1, color = LocalColorScheme.current.onPrimary
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isPin) {
                        Icon(
                            painter = painterResource(R.drawable.ic_push_pin),
                            contentDescription = null, tint = LocalColorScheme.current.onPrimary,
                            modifier = Modifier.size(LocalDimens.current.iconSize.xsm)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                    }
                    Text(
                        text = lastOnline,
                        color = LocalColorScheme.current.onPrimary,
                        fontSize = LocalDimens.current.textSize.small,
                    )
                }
                if (totalMessageUnred > 0) {
                    Spacer(modifier = Modifier.height(LocalDimens.current.sizing.xsmall))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(LocalDimens.current.sizing.small))
                            .background(Red400)
                            .width(LocalDimens.current.sizing.medium)
                            .height(LocalDimens.current.sizing.small + LocalDimens.current.sizing.xsmall)
                    ) {
                        Text(
                            text = "$totalMessageUnred",
                            color = SuperWhite,
                            fontSize = LocalDimens.current.textSize.small,

                            )
                    }
                }

            }
        }
    }
}