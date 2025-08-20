package com.lamnguyen.zalo.ui.search.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lamnguyen.zalo.domain.dtos.User

class SearchViewModel : ViewModel() {
    val searchUserByPhoneNumberResultLiveData: MutableLiveData<User?> = MutableLiveData()
}