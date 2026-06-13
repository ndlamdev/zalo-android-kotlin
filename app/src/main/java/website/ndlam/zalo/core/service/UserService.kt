package website.ndlam.zalo.core.service

import retrofit2.http.GET
import website.ndlam.zalo.data.remote.api.UserInfo
import website.ndlam.zalo.data.remote.api.ApiResponseSucess

interface UserService {
    @GET("user/v1/me")
    suspend fun getInfo(): ApiResponseSucess<UserInfo>


}