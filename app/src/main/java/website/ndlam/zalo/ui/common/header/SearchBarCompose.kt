package website.ndlam.zalo.ui.common.header

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.theme.SuperWhite
import website.ndlam.zalo.ui.theme.dimes
import website.ndlam.zalo.ui.theme.mainScreen
import website.ndlam.zalo.core.util.enums.ScreenOnMainScreen
import website.ndlam.zalo.core.util.enums.ScreenOnMainScreen.CONTACT
import website.ndlam.zalo.core.util.enums.ScreenOnMainScreen.DISCOVER
import website.ndlam.zalo.core.util.enums.ScreenOnMainScreen.MESSAGE
import website.ndlam.zalo.core.util.enums.ScreenOnMainScreen.NEWSFEED
import website.ndlam.zalo.core.util.enums.ScreenOnMainScreen.SETTING
import website.ndlam.zalo.domain.repository.IAuthTokenRepository

@Composable
fun SearchBarCompose(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    currentScreen: ScreenOnMainScreen = MESSAGE,
    onSearchPress: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.mainScreen.headerBarBackground
            )
            .padding(paddingValues)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onSearchPress)
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null, tint = SuperWhite,
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.hint_search),
                color = MaterialTheme.colorScheme.mainScreen.headerBarTextSearch,
                fontSize = MaterialTheme.dimes.textSize.medium
            )


        }
        when (currentScreen) {
            MESSAGE -> SearchRightButtonsOnMessageScreenCompose()
            CONTACT -> SearchRightButtonsOnContactScreenCompose()
            DISCOVER -> SearchRightButtonsOnDiscoverScreenCompose()
            NEWSFEED -> SearchRightButtonsOnDiscoverNewsFeedCompose()
            SETTING -> SearchRightButtonsOnDiscoverSettingCompose()
        }
    }
}

@Composable
fun SearchRightButtonsOnMessageScreenCompose() {
    Row {
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.ic_white_qr), contentDescription = null,
                modifier = Modifier.size(24.dp), tint = SuperWhite
            )
        }
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.ic_plus), contentDescription = null,
                modifier = Modifier.size(24.dp), tint = SuperWhite
            )
        }
    }
}

@Composable
fun SearchRightButtonsOnContactScreenCompose() {
    Row {
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.ic_add_user), contentDescription = null,
                modifier = Modifier.size(24.dp), tint = SuperWhite
            )
        }
    }
}

@Composable
fun SearchRightButtonsOnDiscoverScreenCompose() {
    Row {
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.ic_white_qr), contentDescription = null,
                modifier = Modifier.size(24.dp), tint = SuperWhite
            )
        }
    }
}

@Composable
fun SearchRightButtonsOnDiscoverNewsFeedCompose() {
    Row {
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.ic_add_picture), contentDescription = null,
                modifier = Modifier.size(24.dp), tint = SuperWhite
            )
        }
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.ic_bell),
                contentDescription = null,
                modifier = Modifier.size(24.dp), tint = SuperWhite
            )
        }
    }
}

@Composable
fun SearchRightButtonsOnDiscoverSettingCompose() {
    Row {
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.ic_white_qr), contentDescription = null,
                modifier = Modifier.size(24.dp), tint = SuperWhite
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PreviewSearchBarCompose() {
    SearchBarCompose()
}