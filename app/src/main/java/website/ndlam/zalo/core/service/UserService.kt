package website.ndlam.zalo.core.service

import retrofit2.http.GET
import website.ndlam.zalo.data.remote.api.UserInfo
import website.ndlam.zalo.data.remote.api.ApiResponse

interface UserService {
    @GET("v1/me")
    suspend fun getInfo(): ApiResponse<UserInfo>


}