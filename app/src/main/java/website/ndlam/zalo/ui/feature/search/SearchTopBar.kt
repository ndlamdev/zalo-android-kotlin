package website.ndlam.zalo.ui.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.common.header.BackTopBar
import website.ndlam.zalo.ui.theme.dimes
import website.ndlam.zalo.ui.theme.searchScreen


@Composable
fun SearchTopBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {

    BackTopBar(
        onBack = onBackClick,
        center = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimes.sizing.small))
            BasicTextField(
                value = searchText,
                onValueChange = onSearchTextChange,
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary),
                decorationBox = { innerTextField ->
                    Box {
                        if (searchText.isNotEmpty()) {
                            innerTextField()
                        } else {
                            Text(stringResource(R.string.hint_search), color = Color.Gray)
                        }
                    }
                },
            )
            if (searchText.isNotEmpty()) {
                IconButton(
                    onClick = { onSearchTextChange("") },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clean_search),
                        contentDescription = "Clear",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        },
        right = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_white_qr),
                    contentDescription = "QR Code",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )
}
