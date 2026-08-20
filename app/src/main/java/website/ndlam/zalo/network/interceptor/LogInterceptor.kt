package website.ndlam.zalo.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

object LogInterceptor : Interceptor {
    private val TAG = LogInterceptor::class.java.name

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        Log.d(
            TAG,
            "------------------------------------------------------------------------------------------------------"
        )
        Log.d(TAG, "Request URL: ${request.url}")
        Log.d(TAG, "Method: ${request.method}")
        Log.d(TAG, "Headers")
        request.headers.forEach { header -> Log.d(TAG, "\t+ ${header.first}: ${header.second}") }
        Log.d(TAG, "Body: ${request.body}")
        Log.d(
            TAG,
            "------------------------------------------------------------------------------------------------------"
        )

        return chain.proceed(request)
    }
}