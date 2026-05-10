package website.ndlam.zalo.compose.header

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import website.ndlam.zalo.R
import website.ndlam.zalo.ui.theme.LocalColorScheme
import website.ndlam.zalo.ui.theme.LocalDimens
import website.ndlam.zalo.ui.theme.SuperWhite
import website.ndlam.zalo.utils.enums.ScreenOnMainScreen
import website.ndlam.zalo.utils.enums.ScreenOnMainScreen.*
import website.ndlam.zalo.viewmodels.SearchBarViewModel

@Composable
fun SearchBarCompose(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    currentScreen: ScreenOnMainScreen = MESSAGE,
    viewModel: SearchBarViewModel = viewModel()
) {
    val value = viewModel.searchText.collectAsState().value


    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .background(
                LocalColorScheme.current.headerBarColorScheme.background
            )
            .padding(paddingValues)
    ) {
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null, tint = SuperWhite,
            )
        }
        BasicTextField(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = { text ->
                viewModel.setValue(text)
            },
            textStyle = TextStyle(
                color = LocalColorScheme.current.onPrimary,
                fontSize = LocalDimens.current.textSize.medium
            ),
            cursorBrush = SolidColor(SuperWhite),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(R.string.hint_search),
                            color = Color.LightGray,
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