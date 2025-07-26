package com.lamnguyen.zalo.services

import com.lamnguyen.zalo.dtos.requests.LoginRequest
import com.lamnguyen.zalo.dtos.requests.PhoneNumberRequest
import com.lamnguyen.zalo.dtos.responses.ApiResponseSuccess
import com.lamnguyen.zalo.dtos.responses.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthService {
    @POST("/v1/check-phone-number")
    suspend fun checkPhoneNumber(@Body phoneNumberRequest: PhoneNumberRequest): ApiResponseSuccess<Any>

    @POST("/v1/login")
    suspend fun login(@Body request: LoginRequest): ApiResponseSuccess<LoginResponse>
}