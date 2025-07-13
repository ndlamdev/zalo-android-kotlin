package com.lamnguyen.zalo.ui.main.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val totalMessageUnRead = MutableLiveData<Int>()
    val totalHistoryUnSeen = MutableLiveData<String>()
}