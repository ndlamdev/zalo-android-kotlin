package com.lamnguyen.zalo.domain.responses

import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "phone_number")
    val phoneNumber: String,
    @Json(name = "phone_number_code")
    val phoneNumberCode: String,
    @Json(name = "access_token")
    val accessToken: String
)
