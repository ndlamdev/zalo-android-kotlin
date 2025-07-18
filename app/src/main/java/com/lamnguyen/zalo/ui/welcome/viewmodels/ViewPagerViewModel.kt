package com.lamnguyen.zalo.ui.welcome.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewPagerViewModel : ViewModel() {
    val isParentSwipeLiveData = MutableLiveData(false)
    val dotPositionLiveData = MutableLiveData(0f)
}