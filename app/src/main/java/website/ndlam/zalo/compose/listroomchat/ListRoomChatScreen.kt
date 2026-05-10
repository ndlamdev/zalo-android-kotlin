package website.ndlam.zalo.compose.listroomchat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import website.ndlam.zalo.compose.rootchatcard.RoomChatCard
import website.ndlam.zalo.ui.theme.LocalColorScheme
import website.ndlam.zalo.ui.theme.LocalDimens

@Composable
fun ListRoomChatScreen(paddingValues: PaddingValues = PaddingValues(0.dp)) {
    LazyColumn(
        userScrollEnabled = true,
        modifier = Modifier
            .background(LocalColorScheme.current.primary)
            .padding(paddingValues)
    ) {
        items(20) {
            RoomChatCard(
                iconUrl = "https://blog.jetbrains.com/wp-content/uploads/2021/05/jetpack-2x.png",
                title = "Doan xem",
                lastMessage = "safa sdf asf asfd asf asf asf saf asdf asdf asf asdf asdf",
                isPin = true,
                totalMessageUnred = 10,
                lastOnline = "1 Gio"
            )
        }
    }
}