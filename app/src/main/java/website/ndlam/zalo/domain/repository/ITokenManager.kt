package website.ndlam.zalo.domain.repository

import retrofit2.Response
import website.ndlam.zalo.data.remote.api.ApiResponseSuccess
import website.ndlam.zalo.data.remote.api.LoginInfoResponse

interface ITokenManager {
    suspend fun initialize()
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun getCookieRefreshToken(): String?
    fun setAccessToken(token: String?): Boolean
    fun setRefreshToken(token: String?): Boolean
    fun setCookieRefreshToken(cookie: String?): Boolean
    fun updateTokens(accessToken: String?, cookie: String?): Boolean
    fun clear()
    suspend fun saveLoginInfo(response: Response<ApiResponseSuccess<LoginInfoResponse>>): Boolean
}