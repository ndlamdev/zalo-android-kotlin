package website.ndlam.zalo.compose.header

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import website.ndlam.zalo.R
import website.ndlam.zalo.utils.enums.ScreenOnMainScreen
import website.ndlam.zalo.utils.enums.ScreenOnMainScreen.*
import website.ndlam.zalo.viewmodels.SearchBarViewModel

@Composable
fun SearchBarCompose(
    currentScreen: ScreenOnMainScreen = MESSAGE,
    viewModel: SearchBarViewModel = viewModel()
) {
    val value = viewModel.searchText.collectAsState().value


    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {}) {
            Icon(painter = painterResource(R.drawable.ic_search), contentDescription = null)
        }
        BasicTextField(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = { text ->
                viewModel.setValue(text)
            },
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(R.string.hint_search),
                            color = Color.Gray,
                        )
                    }
                    innerTextField()
                }
            },
        )

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
            Icon(painter = painterResource(R.drawable.ic_white_qr), contentDescription = null)
        }
        IconButton(onClick = {}) {
            Icon(painter = painterResource(R.drawable.ic_white_plus), contentDescription = null)
        }
    }
}

@Composable
fun SearchRightButtonsOnContactScreenCompose() {
    Row {
        IconButton(onClick = {}) {
            Icon(painter = painterResource(R.drawable.ic_add_user), contentDescription = null)
        }
    }
}

@Composable
fun SearchRightButtonsOnDiscoverScreenCompose() {
    Row {
        IconButton(onClick = {}) {
            Icon(painter = painterResource(R.drawable.ic_white_qr), contentDescription = null)
        }
    }
}

@Composable
fun SearchRightButtonsOnDiscoverNewsFeedCompose() {
    Row {
        IconButton(onClick = {}) {
            Icon(painter = painterResource(R.drawable.ic_add_picture), contentDescription = null)
        }
        IconButton(onClick = {}) {
            Icon(painter = painterResource(R.drawable.ic_bell), contentDescription = null)
        }
    }
}

@Composable
fun SearchRightButtonsOnDiscoverSettingCompose() {
    Row {
        IconButton(onClick = {}) {
            Icon(painter = painterResource(R.drawable.ic_white_qr), contentDescription = null)
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PreviewSearchBarCompose() {
    SearchBarCompose()
}