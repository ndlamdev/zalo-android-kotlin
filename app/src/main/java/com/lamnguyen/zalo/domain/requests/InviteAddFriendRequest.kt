package com.lamnguyen.zalo.domain.requests

import com.squareup.moshi.Json

class InviteAddFriendRequest {
    @Json(name = "phone_number")
    lateinit var phoneNumber: String
    lateinit var message: String
}