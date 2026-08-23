package website.ndlam.zalo.ui.feature.conversation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import website.ndlam.zalo.core.util.converter.GsonConverter
import website.ndlam.zalo.core.util.enums.ScreenOnMainScreen
import website.ndlam.zalo.data.remote.api.ApiState
import website.ndlam.zalo.data.remote.api.ConversationDto
import website.ndlam.zalo.navigation.NavDestinations
import website.ndlam.zalo.ui.feature.main.StompMessageViewModel
import website.ndlam.zalo.ui.theme.dimes

@Composable
fun ListConversationScreen(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    navController: NavHostController,
    stompMessageViewModel: StompMessageViewModel
) {
    val viewModel = viewModel<ConversationViewModel>()
    val conversations = viewModel.conversation.collectAsState()

    LaunchedEffect(Unit) {
        if (conversations.value != null) return@LaunchedEffect
        viewModel.loadConversations()
    }

    when (conversations.value) {
        is ApiState.Loading<*> -> {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.dimes.sizing.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        is ApiState.Success<List<ConversationDto>> -> {
            LazyColumn(
                userScrollEnabled = true,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(paddingValues)
            ) {
                items(items = (conversations.value as ApiState.Success<List<ConversationDto>>).data) { item ->
                    ConversationCard(item) { dto ->
                        val json = GsonConverter.gson.toJson(dto)
                        navController.navigate("${NavDestinations.ROOMCHAT.name}/$json") {
                            launchSingleTop = true
                        }
                    }
                }
            }
        }

        else -> {

        }
    }
}