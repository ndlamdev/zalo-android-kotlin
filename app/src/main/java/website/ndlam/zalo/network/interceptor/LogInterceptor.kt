package website.ndlam.zalo.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

object LogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        Log.d("LogInterceptor", "Request URL: ${request.url}")

        return chain.proceed(request)
    }
}