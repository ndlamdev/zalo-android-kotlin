package website.ndlam.zalo.core.service

import retrofit2.http.GET
import retrofit2.http.Query
import website.ndlam.zalo.data.remote.api.ApiResponseSuccess
import website.ndlam.zalo.data.remote.api.UserInfo

interface UserService {
    @GET("user/v1/me")
    suspend fun getInfo(): ApiResponseSuccess<UserInfo>

    @GET("user/v1/search")
    suspend fun search(@Query("phone_Number") phoneNumber: String): ApiResponseSuccess<UserInfo>
}