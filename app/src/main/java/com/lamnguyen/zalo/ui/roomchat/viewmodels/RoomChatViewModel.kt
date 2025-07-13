package com.lamnguyen.zalo.ui.roomchat.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RoomChatViewModel : ViewModel() {
    val id = MutableLiveData<Long>()
    val title = MutableLiveData<String>()
}