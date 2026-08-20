package website.ndlam.zalo.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import website.ndlam.zalo.BuildConfig
import website.ndlam.zalo.core.service.AuthService
import website.ndlam.zalo.core.util.formater.GsonConverter
import website.ndlam.zalo.domain.repository.ITokenManager
import website.ndlam.zalo.network.interceptor.LogInterceptor


object RetrofitClient {
    val authService: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.MAIN_BASE_UTL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(LogInterceptor)
                    .build()
            )
            .addConverterFactory(GsonConverter.converter)
            .build()
            .create(AuthService::class.java)
    }
}