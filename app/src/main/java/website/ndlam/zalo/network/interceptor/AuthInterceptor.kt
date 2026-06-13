package website.ndlam.zalo.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(val token: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .header("Accept", "application/json")
            .build()

        val response = chain.proceed(newRequest)

        return response
    }
}
