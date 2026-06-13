package website.ndlam.zalo.ui.common.textfield.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import website.ndlam.zalo.core.service.AuthService
import website.ndlam.zalo.data.remote.api.LoginInfoRequest
import website.ndlam.zalo.data.remote.api.UserInfo
import website.ndlam.zalo.domain.repository.IAuthTokenRepository
import website.ndlam.zalo.network.RetrofitClient

class PasswordTextFieldViewModel(private val authTokenRepository: IAuthTokenRepository) :
    LocalTextFieldViewModel() {
    private val _hidden = MutableStateFlow(true)
    val hidden: StateFlow<Boolean> = _hidden.asStateFlow()


    fun show() {
        _hidden.value = false
    }

    fun hide() {
        _hidden.value = true
    }

    fun login(phoneNumber: String, password: String): UserInfo? {
        val authService = AuthService.Instant

        viewModelScope.launch {
            val loginResponse = authService.login(LoginInfoRequest(phoneNumber, password))

            authTokenRepository.saveAccessToken(loginResponse.data.accessToken)
        }

        return null
    }

    fun getInfoUser() {
        viewModelScope.launch {
            val token = authTokenRepository.getAccessToken()

            val userService =
                RetrofitClient.userService(token)

            userService.getInfo()
        }
    }
}
