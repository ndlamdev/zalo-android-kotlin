package com.lamnguyen.zalo.services

import com.lamnguyen.zalo.domain.requests.InviteAddFriendRequest
import com.lamnguyen.zalo.domain.responses.ApiResponseSuccess
import retrofit2.http.Body
import retrofit2.http.POST

interface IInviteFriendService {
    @POST("/user/v1/add-friend")
    suspend fun addFriend(@Body request: InviteAddFriendRequest): ApiResponseSuccess<*>
}