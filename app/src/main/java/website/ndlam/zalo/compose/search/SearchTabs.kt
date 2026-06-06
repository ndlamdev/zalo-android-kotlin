package website.ndlam.zalo.compose.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.theme.Blue300
import website.ndlam.zalo.ui.theme.Blue700


@Composable
fun SearchTabs(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
    ) {
        TabItem(
            text = stringResource(R.string.tab_mine),
            count = 3,
            isSelected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )
        Spacer(modifier = Modifier.width(24.dp))
        TabItem(
            text = stringResource(R.string.discover),
            isSelected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        )
    }
}

@Composable
fun TabItem(
    text: String,
    count: Int? = null,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                color = if (isSelected) Blue300 else Color.Gray,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 16.sp
            )
            if (count != null) {
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Blue700),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = count.toString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(2.dp)
                    .background(Blue300)
            )
        } else {
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}
