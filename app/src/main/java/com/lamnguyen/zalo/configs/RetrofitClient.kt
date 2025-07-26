package com.lamnguyen.zalo.configs

import android.content.Context
import com.lamnguyen.zalo.services.IAuthService
import com.lamnguyen.zalo.utils.cookies.MyCookieJar
import com.lamnguyen.zalo.utils.helpers.TokenHelper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.6:8001"
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory()) // Required for Kotlin data classes
        .build()

    private val okHttpClientBuilder = OkHttpClient.Builder()
        .cookieJar(MyCookieJar.getInstance())

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))

    private fun initAuthInterceptor(context: Context): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                val token = TokenHelper.getAccessToken(context)
                val request = originalRequest.newBuilder()
                    .header("AUTHORIZATION", token ?: "")
                    .build()
                return chain.proceed(request)
            }
        }
    }

    val authService: IAuthService by lazy {
        retrofitBuilder
            .client(okHttpClientBuilder.build())
            .build()
            .create(IAuthService::class.java)
    }

    fun authService(context: Context): IAuthService {
        okHttpClientBuilder.addInterceptor(initAuthInterceptor(context))

        return retrofitBuilder
            .client(okHttpClientBuilder.build())
            .build()
            .create(IAuthService::class.java)
    }
}