package website.ndlam.zalo.ui.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import website.ndlam.zalo.ui.common.header.SearchBarCompose
import website.ndlam.zalo.ui.common.menu.MenuBar
import website.ndlam.zalo.core.util.enums.ScreenOnMainScreen

@Composable
fun MainScreen(paddingValues: PaddingValues = PaddingValues(0.dp), onSearchPress: () -> Unit = {}) {
    val pagerState = rememberPagerState { 5 }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBarCompose(
            currentScreen = ScreenOnMainScreen.entries[pagerState.currentPage],
            paddingValues = PaddingValues(top = paddingValues.calculateTopPadding()),
            onSearchPress = onSearchPress
        )
        HorizontalPager(
            pagerState,
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.primary)
        ) { page ->
            when (page) {
                0 -> _root_ide_package_.website.ndlam.zalo.ui.feature.listroomchat.ListRoomChatScreen()
                1 -> _root_ide_package_.website.ndlam.zalo.ui.feature.contact.ContactScreen()
                2 -> _root_ide_package_.website.ndlam.zalo.ui.feature.discovery.DiscoveryScreen()
                3 -> _root_ide_package_.website.ndlam.zalo.ui.feature.newsfeed.NewsFeedScreen()
                else -> _root_ide_package_.website.ndlam.zalo.ui.feature.setting.SettingScreen()
            }
        }
        MenuBar(
            state = ScreenOnMainScreen.entries[pagerState.currentPage], onClick = { key ->
                coroutineScope.launch {
                    pagerState.scrollToPage(ScreenOnMainScreen.entries.indexOf(key))
                }
            },
            paddingValues = PaddingValues(bottom = paddingValues.calculateBottomPadding())
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}