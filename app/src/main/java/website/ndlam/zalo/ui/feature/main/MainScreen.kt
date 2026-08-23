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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import website.ndlam.zalo.core.util.enums.ScreenOnMainScreen
import website.ndlam.zalo.navigation.NavDestinations
import website.ndlam.zalo.ui.common.header.SearchBarCompose
import website.ndlam.zalo.ui.common.menu.MenuBar
import website.ndlam.zalo.ui.feature.contact.ContactScreen
import website.ndlam.zalo.ui.feature.conversation.ListConversationScreen
import website.ndlam.zalo.ui.feature.discovery.DiscoveryScreen
import website.ndlam.zalo.ui.feature.newsfeed.NewsFeedScreen
import website.ndlam.zalo.ui.feature.setting.SettingScreen

@Composable
fun MainScreen(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    navController: NavHostController,
    stompMessageViewModel: StompMessageViewModel = viewModel(),
) {
    val pagerState = rememberPagerState { 5 }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBarCompose(
            currentScreen = ScreenOnMainScreen.entries[pagerState.currentPage],
            paddingValues = PaddingValues(top = paddingValues.calculateTopPadding()),
            onSearchPress = {
                navController.navigate(NavDestinations.Search.name) {
                    launchSingleTop = true
                }
            }
        )
        HorizontalPager(
            pagerState,
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.primary)
        ) { page ->
            when (page) {
                0 -> ListConversationScreen(
                    navController = navController,
                    stompMessageViewModel = stompMessageViewModel
                )

                1 -> ContactScreen()
                2 -> DiscoveryScreen()
                3 -> NewsFeedScreen()
                else -> SettingScreen()
            }
        }
        MenuBar(
            state = ScreenOnMainScreen.entries[pagerState.currentPage],
            onClick = { key ->
                coroutineScope.launch {
                    pagerState.scrollToPage(ScreenOnMainScreen.entries.indexOf(key))
                }
            },
            paddingValues = PaddingValues(bottom = paddingValues.calculateBottomPadding())
        )
    }
}