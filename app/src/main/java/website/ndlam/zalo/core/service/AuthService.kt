package website.ndlam.zalo.core.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import website.ndlam.zalo.network.RetrofitClient
import website.ndlam.zalo.data.remote.api.LoginInfoRequest
import website.ndlam.zalo.data.remote.api.ApiResponseSucess
import website.ndlam.zalo.data.remote.api.LoginInfoResponse

interface AuthService {
    @POST("auth/v1/login")
    suspend fun login(@Body body: LoginInfoRequest): Response<ApiResponseSucess<LoginInfoResponse>>

    companion object {
        val Instant = RetrofitClient.authService
    }
}