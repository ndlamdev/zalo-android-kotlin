package website.ndlam.zalo.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.asResponseBody
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import website.ndlam.zalo.core.service.AuthService
import website.ndlam.zalo.core.service.UserService
import website.ndlam.zalo.data.remote.api.ApiResponseError
import website.ndlam.zalo.network.interceptor.AuthInterceptor
import website.ndlam.zalo.network.interceptor.LogInterceptor

private val gson = GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .create()

object RetrofitClient {
    const val MAIN_BASE_UTL = "https://zola.ndlam.online"

    private val converter = GsonConverterFactory.create(gson)

    val authService: AuthService by lazy {

        Retrofit.Builder()
            .baseUrl(MAIN_BASE_UTL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(LogInterceptor)
                    .build()
            )
            .addConverterFactory(converter)
            .build()
            .create(AuthService::class.java)
    }

    fun userService(token: String?): UserService {
        return Retrofit.Builder()
            .baseUrl(MAIN_BASE_UTL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(token))
                    .build()
            )
            .addConverterFactory(converter)
            .build()
            .create(UserService::class.java)
    }
}

fun HttpException.getResponseError(): ApiResponseError? {
    val response = this.response() ?: return null

    val errorBody = response.errorBody() ?: return null

    val json = errorBody.string()

    return gson.fromJson(json, ApiResponseError::class.java)
}