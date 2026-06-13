package website.ndlam.zalo.ui.feature.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.theme.Blue300
import website.ndlam.zalo.ui.theme.Blue900
import website.ndlam.zalo.ui.theme.Gray550
import website.ndlam.zalo.ui.theme.dimes


@Composable
fun FriendItem(
    name: String,
    phoneNumber: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimes.sizing.mediumAddXsmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = ColorPainter(Color.Gray),
            contentDescription = null,
            modifier = Modifier
                .size(MaterialTheme.dimes.iconSize.xl)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(MaterialTheme.dimes.sizing.mediumAddXsmall))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                color = Color.White,
                fontSize = MaterialTheme.dimes.textSize.large,
                fontWeight = FontWeight.Medium
            )
            Row {
                Text(
                    text = stringResource(R.string.phone_number),
                    color = Gray550,
                    fontSize = MaterialTheme.dimes.textSize.medium
                )
                Text(
                    text = phoneNumber,
                    color = Blue300,
                    fontSize = MaterialTheme.dimes.textSize.medium
                )
            }
        }
        Box(
            modifier = Modifier
                .size(MaterialTheme.dimes.iconSize.lg)
                .clip(CircleShape)
                .background(Blue900.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_call),
                contentDescription = "Call",
                tint = Blue300,
                modifier = Modifier.size(MaterialTheme.dimes.iconSize.sm)
            )
        }
    }
}