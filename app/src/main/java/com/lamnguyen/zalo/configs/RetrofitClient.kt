package com.lamnguyen.zalo.configs

import android.content.Context
import android.content.Intent
import com.lamnguyen.zalo.domain.responses.LoginResponse
import com.lamnguyen.zalo.services.IAuthService
import com.lamnguyen.zalo.services.IUserService
import com.lamnguyen.zalo.ui.login.LoginActivity
import com.lamnguyen.zalo.utils.cookies.AppCookieJar
import com.lamnguyen.zalo.utils.helpers.TokenHelper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.6:8000"
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory()) // Required for Kotlin data classes
//        .add(LocalDateAdapter())
        .build()

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))

    private fun initAuthInterceptor(context: Context): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                val token = TokenHelper.getAccessToken(context)
                val request = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
                val response = chain.proceed(request)
                val isExpire = !response.header("X-Jwt-Expired").isNullOrEmpty()
                if (isExpire) {
                    return recallApi(context, chain, response)
                }
                val code = response.code
                if (code == 401) {
                    TokenHelper.cleanAccessToken(context)
                    context.startActivity(Intent(context, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                }
                return response
            }
        }
    }

    private fun recallApi(
        context: Context,
        chain: Interceptor.Chain,
        response: Response
    ): Response {
        val newResponse = resign(context)
        if (newResponse == null) {
            return response
        }

        val newRequest = chain.request().newBuilder()
            .header("Authorization", newResponse.accessToken)
            .build()

        response.close()
        return chain.proceed(newRequest)
    }

    private fun resign(context: Context): LoginResponse? {
        val service = retrofitBuilder
            .client(
                OkHttpClient.Builder()
                    .cookieJar(AppCookieJar.getInstance(context)).build()
            )
            .build()
            .create(IAuthService::class.java)
        val response = service.resign().execute().body()?.data
        TokenHelper.saveAccessToken(response?.accessToken, context)

        return response
    }

    val authService: IAuthService by lazy {
        retrofitBuilder
            .client(createOkHttpClientBuild().build())
            .build()
            .create(IAuthService::class.java)
    }

    fun authService(context: Context?): IAuthService {
        return retrofitBuilder
            .client(createOkHttpClientBuild(context).build())
            .build()
            .create(IAuthService::class.java)
    }

    fun userService(context: Context?): IUserService {
        return retrofitBuilder
            .client(createOkHttpClientBuild(context).build())
            .build()
            .create(IUserService::class.java)
    }

    private fun createOkHttpClientBuild(context: Context? = null): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        if (context != null)
            builder
                .cookieJar(AppCookieJar.getInstance(context))
                .addInterceptor(initAuthInterceptor(context))

        return builder
    }
}