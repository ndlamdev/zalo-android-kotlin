package com.lamnguyen.zalo.ui.main.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {
    val pageIndexLiveData: MutableLiveData<Int> = MutableLiveData(0)
}