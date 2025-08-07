package com.lamnguyen.zalo.services

import com.lamnguyen.zalo.domain.dtos.User
import com.lamnguyen.zalo.domain.responses.ApiResponseSuccess
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IUserService {
    @GET("/user/v1/search")
    suspend fun searchUser(@Query(value = "phone_number") phoneNumber: String): Response<ApiResponseSuccess<User>>
}