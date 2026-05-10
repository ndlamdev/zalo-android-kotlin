package website.ndlam.zalo.compose.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import website.ndlam.zalo.compose.contact.ContactScreen
import website.ndlam.zalo.compose.discovery.DiscoveryScreen
import website.ndlam.zalo.compose.header.SearchBarCompose
import website.ndlam.zalo.compose.listroomchat.ListRoomChatScreen
import website.ndlam.zalo.compose.menu.MenuBar
import website.ndlam.zalo.compose.newsfeed.NewsFeedScreen
import website.ndlam.zalo.compose.setting.SettingScreen
import website.ndlam.zalo.ui.theme.LocalColorScheme
import website.ndlam.zalo.utils.enums.ScreenOnMainScreen
import website.ndlam.zalo.viewmodels.StateMainScreenViewModel

@Composable
fun MainScreen(paddingValues: PaddingValues = PaddingValues(0.dp)) {
    val pagerState = rememberPagerState { 5 }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBarCompose(
            currentScreen = ScreenOnMainScreen.entries[pagerState.currentPage],
            paddingValues = PaddingValues(top = paddingValues.calculateTopPadding())
        )
        HorizontalPager(pagerState, modifier = Modifier.weight(1f)) { page ->
            when (page) {
                0 -> ListRoomChatScreen()
                1 -> ContactScreen()
                2 -> DiscoveryScreen()
                3 -> NewsFeedScreen()
                else -> SettingScreen()
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