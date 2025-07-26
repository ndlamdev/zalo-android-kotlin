package com.lamnguyen.zalo.dtos.requests

import com.squareup.moshi.Json

data class LoginRequest(
    @Json(name = "phone_number")
    val phoneNumber: String,
    val password: String
)
