package website.ndlam.zalo.ui.common.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import website.ndlam.zalo.data.remote.api.ApiResponseSuccess
import website.ndlam.zalo.data.remote.api.ApiState
import website.ndlam.zalo.data.remote.api.LoginInfoRequest
import website.ndlam.zalo.data.remote.api.LoginInfoResponse
import website.ndlam.zalo.domain.repository.ITokenManager
import website.ndlam.zalo.network.RetrofitClient
import website.ndlam.zalo.network.RetrofitClientSecured
import website.ndlam.zalo.network.helper.getResponseError

class AuthViewModel(private val authManager: ITokenManager) : ViewModel() {
    private val _loginStatus = MutableStateFlow<ApiState<Nothing>?>(null)
    val loginStatus = _loginStatus.asStateFlow()

    fun login(phoneNumber: String, password: String, loginFailedMessage: String = "") {
        viewModelScope.launch {
            try {
                _loginStatus.value = ApiState.Loading()
                val loginResponse =
                    RetrofitClient.authService.login(LoginInfoRequest(phoneNumber, password))

                saveLoginInfo(loginResponse, loginFailedMessage)
            } catch (e: HttpException) {
                val response = e.getResponseError()
                _loginStatus.value =
                    ApiState.Error(response?.detail ?: response?.error ?: loginFailedMessage)

            }
        }
    }

    fun info() {
        viewModelScope.launch {
            try {
                _loginStatus.value = ApiState.Loading()
                authManager.initialize()
                val authService = RetrofitClientSecured.authService

                authService.info()
                _loginStatus.value = ApiState.SuccessNotResponse()
            } catch (e: HttpException) {
                val response = e.getResponseError()
                _loginStatus.value =
                    ApiState.Error(response?.detail ?: response?.error ?: "Something wrong!")
            } catch (e: Exception) {
                _loginStatus.value =
                    ApiState.Error(e.message ?: "Something wrong!")
            }
        }
    }

    fun clearLoginStatus() {
        _loginStatus.value = null
    }

    private suspend fun saveLoginInfo(
        loginResponse: Response<ApiResponseSuccess<LoginInfoResponse>>,
        loginFailedMessage: String
    ) {
        if (!authManager.saveLoginInfo(loginResponse)) {
            _loginStatus.value = ApiState.Error(loginFailedMessage)
            return
        }

        _loginStatus.value = ApiState.SuccessNotResponse()
    }
}