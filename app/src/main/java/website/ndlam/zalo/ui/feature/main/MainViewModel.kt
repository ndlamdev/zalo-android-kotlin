package website.ndlam.zalo.ui.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import website.ndlam.zalo.data.remote.api.ApiState
import website.ndlam.zalo.data.remote.api.UserInfo
import website.ndlam.zalo.domain.repository.IAuthTokenRepository
import website.ndlam.zalo.network.RetrofitClient
import website.ndlam.zalo.network.getResponseError

class MainViewModel(val authTokenRepository: IAuthTokenRepository) :
    ViewModel() {
    private val _user = MutableStateFlow<ApiState<UserInfo>>(ApiState.Loading())
    val user: StateFlow<ApiState<UserInfo>> = _user.asStateFlow()

    fun getUserInfo() {
        viewModelScope.launch {
            val token = authTokenRepository.getAccessToken()

            val service = RetrofitClient.userService(token)

            try {
                _user.value = ApiState.Loading()
                val response = service.getInfo()

                _user.value = ApiState.Success(response.data)
            } catch (e: HttpException) {
                val response = e.getResponseError()
                _user.value =
                    ApiState.Error(response?.detail ?: response?.error ?: "Lỗi hệ thống!")
            }
        }
    }

}