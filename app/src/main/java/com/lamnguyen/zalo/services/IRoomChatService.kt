package com.lamnguyen.zalo.services

import com.lamnguyen.zalo.entities.RoomChat
import com.lamnguyen.zalo.domain.requests.CreateRoomChatRequest
import com.lamnguyen.zalo.domain.responses.ApiResponseSuccess
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IRoomChatService {
    @GET("/chat/v1")
    suspend fun getAllRoomChat(): ApiResponseSuccess<List<RoomChat>>

    @POST("/chat/v1/create-room-chat")
    suspend fun createRoomChat(@Body request: CreateRoomChatRequest): ApiResponseSuccess<*>
}