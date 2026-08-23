package website.ndlam.zalo.network

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import website.ndlam.zalo.core.util.converter.GsonConverter
import website.ndlam.zalo.core.util.converter.convert
import website.ndlam.zalo.data.remote.api.ApiResponseError
import website.ndlam.zalo.domain.repository.ITokenManager

class TokenAuthenticator(val tokenManager: ITokenManager?) : Authenticator {
    val mutex = Mutex()

    override fun authenticate(
        route: Route?,
        response: Response
    ): Request? {
        if (responseCount(response) >= 2 || response.code != 401) return null

        val body = GsonConverter.convert<ApiResponseError>(response.body?.bytes()) ?: return null

        if (body.code != 9999) return null

        val oldToken = response.request.header("Authorization")?.substring("Bearer ".length)

        val newToken = runBlocking {
            refreshToken(oldToken)
        }

        if (newToken.isNullOrBlank()) return null

        return response.request
            .newBuilder()
            .header("Authorization", "Bearer $newToken")
            .build()
    }

    private suspend fun refreshToken(
        failedToken: String?
    ): String? {
        return mutex.withLock {
            val currentToken = tokenManager?.getAccessToken()

            // Request khác đã refresh rồi
            if (currentToken != null && currentToken != failedToken) {
                return@withLock currentToken
            }

            val refreshToken = tokenManager?.getCookieRefreshToken() ?: return@withLock null

            // Thực sự cần refresh
            val response = RetrofitClient.authService.refresh(refreshToken)

            if (!response.isSuccessful) {
                tokenManager.clear()
                return@withLock null
            }

            tokenManager.saveLoginInfo(response)

            response.body()?.data?.token
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse

        while (prior != null) {
            count++
            prior = prior.priorResponse
        }

        return count
    }
}