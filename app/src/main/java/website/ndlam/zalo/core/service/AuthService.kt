package website.ndlam.zalo.core.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import website.ndlam.zalo.network.RetrofitClient
import website.ndlam.zalo.data.remote.api.LoginInfoRequest
import website.ndlam.zalo.data.remote.api.ApiResponseSuccess
import website.ndlam.zalo.data.remote.api.LoginInfoResponse

interface AuthService {
    @POST("auth/v1/login")
    suspend fun login(@Body body: LoginInfoRequest): Response<ApiResponseSuccess<LoginInfoResponse>>

    @GET("auth/v1/info")
    suspend fun info(): Response<ApiResponseSuccess<Nothing>>

    companion object {
        val Instant = RetrofitClient.authService
    }
}