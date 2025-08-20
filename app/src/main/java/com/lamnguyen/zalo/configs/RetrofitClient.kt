package com.lamnguyen.zalo.configs

import android.content.Context
import android.content.Intent
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.lamnguyen.zalo.domain.responses.ApiResponseError
import com.lamnguyen.zalo.domain.responses.LoginResponse
import com.lamnguyen.zalo.services.IAuthService
import com.lamnguyen.zalo.services.IInviteFriendService
import com.lamnguyen.zalo.services.IRoomChatService
import com.lamnguyen.zalo.services.IUserService
import com.lamnguyen.zalo.ui.login.LoginActivity
import com.lamnguyen.zalo.utils.cookies.AppCookieJar
import com.lamnguyen.zalo.utils.helpers.TokenHelper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Duration

object RetrofitClient {
    private const val BASE_URL = "https://zalo.ndlamdev.website"
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
        response: Response,
    ): Response {
        val token = AppCookieJar.getInstance(context).loadForRequest(chain.request().url)
        if (token.isEmpty()) return response
        try {
            response.close()
            val authResponse = resign(context)

            val newRequest = chain.request().newBuilder()
                .header("Authorization", "Bearer ${authResponse!!.accessToken}")
                .build()

            return chain.proceed(newRequest)
        } catch (_: Exception) {
            return chain.proceed(chain.request())
        }
    }

    private fun resign(context: Context): LoginResponse? {
        Log.i(this.javaClass.name, "Resign")
        val service = authService(context, false)
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

    fun authService(context: Context?, isAuth: Boolean? = true): IAuthService {
        return retrofitBuilder
            .client(createOkHttpClientBuild(context, isAuth).build())
            .build()
            .create(IAuthService::class.java)
    }

    fun userService(context: Context?, isAuth: Boolean? = true): IUserService {
        return retrofitBuilder
            .client(createOkHttpClientBuild(context, isAuth).build())
            .build()
            .create(IUserService::class.java)
    }

    fun roomChatService(context: Context?, isAuth: Boolean? = true): IRoomChatService {
        return retrofitBuilder
            .client(createOkHttpClientBuild(context, isAuth).build())
            .build()
            .create(IRoomChatService::class.java)
    }

    fun inviteFriendService(context: Context?, isAuth: Boolean? = true): IInviteFriendService {
        return retrofitBuilder
            .client(createOkHttpClientBuild(context, isAuth).build())
            .build()
            .create(IInviteFriendService::class.java)
    }

    private fun createOkHttpClientBuild(
        context: Context? = null,
        isAuth: Boolean? = true,
    ): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .callTimeout(Duration.ofSeconds(10))
        if (context != null) {
            builder
                .cookieJar(AppCookieJar.getInstance(context))
            if (isAuth == true)
                builder.addInterceptor(initAuthInterceptor(context))
        }

        return builder
    }

    fun convertToResponseError(e: HttpException): ApiResponseError<*> {
        val body = e.response()?.errorBody()?.byteStream()
        return ObjectMapper().readValue(body, ApiResponseError::class.java)
    }
}