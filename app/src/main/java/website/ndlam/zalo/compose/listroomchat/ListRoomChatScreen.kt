package website.ndlam.zalo.compose.listroomchat

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import website.ndlam.zalo.ui.theme.Red300

@Composable
fun ListRoomChatScreen(paddingValues: PaddingValues = PaddingValues(0.dp)) {
    LazyColumn(
        userScrollEnabled = true,
        modifier = Modifier
            .fillMaxHeight()
            .padding(paddingValues)
    ) {
        items(items = dataTest) { item ->
            RoomChatCard(item)
        }
    }
}

val dataTest = arrayOf(
    RoomChatInfo(
        iconUrl = "https://blog.jetbrains.com/wp-content/uploads/2021/05/jetpack-2x.png",
        title = "Doan xem",
        lastMessage = "safa sdf asf asfd asf asf asf saf asdf asdf asf asdf asdf",
        isPin = true,
        totalMessageUnread = 10,
        lastOnline = "1 Gio"
    ), RoomChatInfo(
        iconUrl = "https://blog.jetbrains.com/wp-content/uploads/2021/05/jetpack-2x.png",
        title = "Doan xem",
        lastMessage = "safa sdf asf asfd asf asf asf saf asdf asdf asf asdf asdf",
        isPin = true,
        lastOnline = "1 Gio"
    ), RoomChatInfo(
        iconUrl = "https://blog.jetbrains.com/wp-content/uploads/2021/05/jetpack-2x.png",
        title = "Doan xem",
        lastMessage = "safa sdf asf asfd asf asf asf saf asdf asdf asf asdf asdf",
        totalMessageUnread = 10,
        lastOnline = "1 Gio"
    ), RoomChatInfo(
        iconUrl = "https://blog.jetbrains.com/wp-content/uploads/2021/05/jetpack-2x.png",
        title = "Doan xem",
        lastMessage = "safa sdf asf asfd asf asf asf saf asdf asdf asf asdf asdf",
        lastOnline = "1 Gio"
    )
)