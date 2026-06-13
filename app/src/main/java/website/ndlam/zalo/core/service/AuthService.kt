package website.ndlam.zalo.core.service

import retrofit2.http.Body
import retrofit2.http.POST
import website.ndlam.zalo.network.RetrofitClient
import website.ndlam.zalo.data.remote.api.LoginInfoRequest
import website.ndlam.zalo.data.remote.api.ApiResponse
import website.ndlam.zalo.data.remote.api.LoginInfoResponse

interface AuthService {
    @POST("v1/login")
    suspend fun login(@Body body: LoginInfoRequest): ApiResponse<LoginInfoResponse>

    companion object {
        val Instant = RetrofitClient.authService
    }
}