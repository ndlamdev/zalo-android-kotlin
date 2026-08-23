package website.ndlam.zalo.ui.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import website.ndlam.zalo.data.remote.api.ApiState
import website.ndlam.zalo.data.remote.api.UserInRelationShip
import website.ndlam.zalo.network.RetrofitClientSecured
import website.ndlam.zalo.network.helper.getResponseError

class MainViewModel :
    ViewModel() {
    private val _user = MutableStateFlow<ApiState<UserInRelationShip>>(ApiState.Loading())
    val user: StateFlow<ApiState<UserInRelationShip>> = _user.asStateFlow()

    fun getUserInfo() {
        viewModelScope.launch {
            val service = RetrofitClientSecured.userService

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