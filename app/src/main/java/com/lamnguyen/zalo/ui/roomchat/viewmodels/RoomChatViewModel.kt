package com.lamnguyen.zalo.ui.roomchat.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RoomChatViewModel : ViewModel() {
    val roomChatIdLiveData = MutableLiveData<String>()
    val roomChatTitleLiveData = MutableLiveData<String>()
    val roomChatIsGroupLiveData = MutableLiveData(false)
}