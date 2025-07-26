package com.lamnguyen.zalo.ui.login.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PhoneNumberViewModel : ViewModel() {
    val phoneNumberLiveData = MutableLiveData("")
    val phoneNumberCodeLiveData = MutableLiveData("")
}