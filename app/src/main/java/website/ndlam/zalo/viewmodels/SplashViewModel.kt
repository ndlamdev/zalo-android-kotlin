package website.ndlam.zalo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import website.ndlam.zalo.data.UserInfo
import website.ndlam.zalo.utils.enums.ApiCallingStatus


class SplashViewModel : ViewModel() {
    private val _loadInfoUserState =
        MutableStateFlow<ApiCallingStatus<UserInfo>>(ApiCallingStatus.Loading)
    val loadInfoUserState: StateFlow<ApiCallingStatus<UserInfo>> = _loadInfoUserState


    fun loadInfo() {
        viewModelScope.launch {
            try {
                delay(1000)
                _loadInfoUserState.value =
                    ApiCallingStatus.Success(UserInfo("Lam Nguyen"))
            } catch (e: Exception) {
                _loadInfoUserState.value = ApiCallingStatus.Error("Doan Xem")
            }
        }
    }
}