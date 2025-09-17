package com.lamnguyen.zalo.ui.main.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lamnguyen.zalo.entities.Message
import com.lamnguyen.zalo.entities.RoomChat
import java.io.Serializable
import java.time.Instant

class MessageFragmentViewModel : ViewModel() {
    val roomChatLiveData = MutableLiveData<Map<String, RoomChatDetail>>()

    class RoomChatDetail : RoomChat(), Serializable {
        val messages = mutableListOf<Message>()
        var pin: Boolean = false
        var totalMessageUnread: Int = 0
        val timeLastAction: Instant = Instant.now()
    }

    fun addMessage(message: Message) {
        roomChatLiveData.value?.get(message.roomChatId)?.messages?.add(message)
    }
}