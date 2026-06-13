package website.ndlam.zalo.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import website.ndlam.zalo.network.interceptor.AuthInterceptor
import website.ndlam.zalo.core.service.AuthService
import website.ndlam.zalo.core.service.UserService

object RetrofitClient {
    const val MAIN_BASE_UTL = "http://localhost:8080"

    val authService: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl(MAIN_BASE_UTL)
            .addConverterFactory(GsonConverterFactory.create())
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
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }
}