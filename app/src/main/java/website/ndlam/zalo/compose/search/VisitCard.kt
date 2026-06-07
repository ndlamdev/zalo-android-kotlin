package website.ndlam.zalo.compose.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.theme.Blue300
import website.ndlam.zalo.ui.theme.Gray550
import website.ndlam.zalo.ui.theme.dimes
import website.ndlam.zalo.ui.theme.searchScreen


data class VisitCardInfo(
    val name: String,
    val phoneNumber: String
)

@Composable
fun VisitCard(info: VisitCardInfo) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.searchScreen.messageBorder,
                RoundedCornerShape(MaterialTheme.dimes.sizing.small)
            )
            .background(MaterialTheme.colorScheme.surface)
            .padding(MaterialTheme.dimes.sizing.smallAddXsmall)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = ColorPainter(Color.Gray),
                contentDescription = null,
                modifier = Modifier
                    .size(MaterialTheme.dimes.sizing.largeAddXsmall)
                    .clip(RoundedCornerShape(MaterialTheme.dimes.sizing.xsmall)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimes.sizing.smallAddXsmall))
            Column {
                Text(
                    text = info.name,
                    color = Gray550,
                    fontSize = MaterialTheme.dimes.textSize.medium,
                    fontWeight = FontWeight.Medium
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_id_card),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(MaterialTheme.dimes.iconSize.sm)
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.dimes.sizing.xsmall))
                    Text(
                        text = info.phoneNumber,
                        color = Blue300,
                        fontSize = MaterialTheme.dimes.textSize.medium,
                    )
                }
            }
        }
    }
}