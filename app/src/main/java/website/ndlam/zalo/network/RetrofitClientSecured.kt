package website.ndlam.zalo.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import website.ndlam.zalo.BuildConfig
import website.ndlam.zalo.core.service.AuthService
import website.ndlam.zalo.core.service.ChatService
import website.ndlam.zalo.core.service.UserService
import website.ndlam.zalo.core.util.converter.GsonConverter
import website.ndlam.zalo.domain.repository.ITokenManager
import website.ndlam.zalo.network.interceptor.AuthInterceptor
import website.ndlam.zalo.network.interceptor.LogInterceptor

object RetrofitClientSecured {
    var tokenManager: ITokenManager? = null

    private val defaultOkHttpClientBuilder: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder()
            .addInterceptor(LogInterceptor)
            .addInterceptor(AuthInterceptor(tokenManager))
            .authenticator(TokenAuthenticator(tokenManager))
    }

    val client: OkHttpClient by lazy {
        defaultOkHttpClientBuilder.build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.MAIN_BASE_UTL)
            .client(client)
            .addConverterFactory(GsonConverter.converter)
            .build()
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    val chatService: ChatService by lazy {
        retrofit.create(ChatService::class.java)
    }
}