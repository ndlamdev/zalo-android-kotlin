package website.ndlam.zalo.ui.feature.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Cookie
import retrofit2.HttpException
import retrofit2.Response
import website.ndlam.zalo.core.service.AuthService
import website.ndlam.zalo.data.remote.api.ApiResponseSucess
import website.ndlam.zalo.data.remote.api.LoginInfoRequest
import website.ndlam.zalo.data.remote.api.ApiState
import website.ndlam.zalo.data.remote.api.LoginInfoResponse
import website.ndlam.zalo.domain.repository.IAuthTokenRepository
import website.ndlam.zalo.network.getResponseError

class PasswordViewModel(private val authTokenRepository: IAuthTokenRepository) : ViewModel() {
    private val _loginStatus = MutableStateFlow<ApiState<Nothing>>(ApiState.Loading())
    val loginStatus = _loginStatus.asStateFlow()

    fun login(phoneNumber: String, password: String, loginFailedMessage: String = "") {
        val authService = AuthService.Instant

        viewModelScope.launch {
            try {
                _loginStatus.value = ApiState.Loading()
                val loginResponse = authService.login(LoginInfoRequest(phoneNumber, password))

                saveToken(loginResponse, loginFailedMessage)


            } catch (e: HttpException) {
                val response = e.getResponseError()
                _loginStatus.value =
                    ApiState.Error(response?.detail ?: response?.error ?: loginFailedMessage)

            }
        }
    }

    private suspend fun saveToken(
        loginResponse: Response<ApiResponseSucess<LoginInfoResponse>>,
        loginFailedMessage: String
    ) {
        val cookies = loginResponse.headers().values("set-cookie")

        for (cookie in cookies) {
            if (cookie.startsWith("REFRESH_TOKEN")) {
                val ck = Cookie.parse(
                    loginResponse.raw().request.url,
                    cookie
                )

                authTokenRepository.saveRefreshToken(ck?.value)


            }
        }
        val body = loginResponse.body()

        if (body == null) {
            _loginStatus.value =
                ApiState.Error(loginFailedMessage)

            return
        }


        authTokenRepository.saveAccessToken(body.data.accessToken)
        authTokenRepository.saveRegion(body.data.phoneNumberCode)
        authTokenRepository.savePhoneNumber(body.data.phoneNumber)
        _loginStatus.value = ApiState.SuccessNotResponse()
    }
}