package com.lamnguyen.zalo.domain.requests

import com.squareup.moshi.Json

data class PhoneNumberRequest(
    @Json(name = "phone_number")
    val phoneNumber: String
)
