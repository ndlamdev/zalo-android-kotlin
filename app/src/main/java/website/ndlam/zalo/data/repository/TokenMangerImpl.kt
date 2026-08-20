package website.ndlam.zalo.data.repository

import retrofit2.Response
import website.ndlam.zalo.BuildConfig
import website.ndlam.zalo.data.remote.api.ApiResponseSuccess
import website.ndlam.zalo.data.remote.api.LoginInfoResponse
import website.ndlam.zalo.domain.repository.IAuthRepository
import website.ndlam.zalo.domain.repository.ITokenManager
import website.ndlam.zalo.network.helper.getCookieRefreshToken

class TokenMangerImpl(private val authRepository: IAuthRepository) : ITokenManager {
    @Volatile
    private var accessToken: String? = null

    @Volatile
    private var refreshToken: String? = null

    @Volatile
    private var cookieRefreshToken: String? = null

    override suspend fun initialize() {
        if (accessToken != null) return
        accessToken = authRepository.getAccessToken()
        refreshToken = authRepository.getRefreshToken()
        cookieRefreshToken = authRepository.getCookieRefreshToken()
    }

    override fun getAccessToken(): String? {
        return accessToken
    }

    override fun getRefreshToken(): String? {
        return refreshToken
    }

    override fun getCookieRefreshToken(): String? {
        return cookieRefreshToken
    }

    override fun setAccessToken(token: String?): Boolean {
        if (token.isNullOrBlank()) return false

        this.accessToken = token
        return true
    }

    override fun setRefreshToken(token: String?): Boolean {
        if (token.isNullOrBlank()) return false

        this.refreshToken = token
        return true
    }

    override fun setCookieRefreshToken(cookie: String?): Boolean {
        if (cookie.isNullOrBlank() || !cookie.startsWith(BuildConfig.REFRESH_TOKEN_COOKIE)) return false

        this.cookieRefreshToken = cookie
        return true
    }

    override fun updateTokens(accessToken: String?, cookie: String?): Boolean {
        return setAccessToken(accessToken) and
                setCookieRefreshToken(cookie) and
                setRefreshToken(refreshToken)
    }

    override fun clear() {
        accessToken = null
        refreshToken = null
    }

    override suspend fun saveLoginInfo(response: Response<ApiResponseSuccess<LoginInfoResponse>>): Boolean {
        val cookie = response.getCookieRefreshToken() ?: return false
        val body = response.body()?.data ?: return false
        if (!authRepository.saveAuthInfo(body, cookie)) return false
        updateTokens(body.token, cookie)
        return true
    }
}