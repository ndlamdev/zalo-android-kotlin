package com.lamnguyen.zalo.services

import com.lamnguyen.zalo.domain.requests.LoginRequest
import com.lamnguyen.zalo.domain.requests.PhoneNumberRequest
import com.lamnguyen.zalo.domain.responses.ApiResponseSuccess
import com.lamnguyen.zalo.domain.responses.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthService {
    @POST("/auth/v1/check-phone-number")
    suspend fun checkPhoneNumber(@Body phoneNumberRequest: PhoneNumberRequest): ApiResponseSuccess<Any>

    @POST("/auth/v1/login")
    suspend fun login(@Body request: LoginRequest): ApiResponseSuccess<LoginResponse>

    @POST("/auth/v1/resign")
    fun resign(): Call<ApiResponseSuccess<LoginResponse>>

    @POST("/auth/v1/resign")
    suspend fun resignSuspend(): ApiResponseSuccess<LoginResponse>

    @POST("/auth/v1/logout")
    suspend fun logout(): ApiResponseSuccess<*>
}