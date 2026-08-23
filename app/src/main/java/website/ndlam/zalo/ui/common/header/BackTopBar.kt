package website.ndlam.zalo.ui.common.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.theme.dimes
import website.ndlam.zalo.ui.theme.searchScreen

@Composable
fun BackTopBar(
    onBack: () -> Unit = {},
    center: @Composable RowScope.() -> Unit = {},
    right: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.searchScreen.topBarBackground)
            .padding(horizontal = MaterialTheme.dimes.sizing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBack,
            colors = IconButtonDefaults.iconButtonColors().copy(contentColor = Color.Transparent)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_white_arrow_left),
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .clip(RoundedCornerShape(MaterialTheme.dimes.sizing.small))
                .background(MaterialTheme.colorScheme.searchScreen.topBarInputBackground)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            center()
        }

        right()
    }
}