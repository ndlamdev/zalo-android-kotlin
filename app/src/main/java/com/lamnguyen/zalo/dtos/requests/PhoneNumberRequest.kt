package com.lamnguyen.zalo.dtos.requests

import com.squareup.moshi.Json

data class PhoneNumberRequest(
    @Json(name = "phone_number")
    val phoneNumber: String
)
