package website.ndlam.zalo.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import website.ndlam.zalo.domain.repository.ITokenManager


class AuthInterceptor(val tokenManager: ITokenManager?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer ${tokenManager?.getAccessToken()}")
            .header("Accept", "application/json")
            .build()

        val response = chain.proceed(newRequest)

        return response
    }
}
