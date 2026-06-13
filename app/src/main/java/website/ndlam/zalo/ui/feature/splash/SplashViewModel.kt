package website.ndlam.zalo.ui.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import website.ndlam.zalo.data.remote.api.UserInfo
import website.ndlam.zalo.core.util.enums.ApiCallingStatus
import kotlin.time.Duration.Companion.milliseconds

class SplashViewModel : ViewModel() {
    private val _loadInfoUserState =
        MutableStateFlow<ApiCallingStatus<UserInfo>>(ApiCallingStatus.Loading)
    val loadInfoUserState: StateFlow<ApiCallingStatus<UserInfo>> = _loadInfoUserState


    fun loadInfo() {
        viewModelScope.launch {
            try {
                delay(1000.milliseconds)
                _loadInfoUserState.value =
                    ApiCallingStatus.Success(UserInfo("Lam Nguyen"))
            } catch (_: Exception) {
                _loadInfoUserState.value = ApiCallingStatus.Error("Doan Xem")
            }
        }
    }
}