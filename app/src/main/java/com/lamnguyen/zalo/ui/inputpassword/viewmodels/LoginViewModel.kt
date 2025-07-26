package com.lamnguyen.zalo.ui.inputpassword.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    val phoneNumberCodeLiveData = MutableLiveData("")
    val phoneNumberLiveData = MutableLiveData("")
    val passwordLiveData = MutableLiveData("")
}