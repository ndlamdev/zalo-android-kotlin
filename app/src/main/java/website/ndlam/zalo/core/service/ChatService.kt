package website.ndlam.zalo.core.service

import retrofit2.http.GET
import website.ndlam.zalo.data.remote.api.ApiResponseSuccess
import website.ndlam.zalo.data.remote.api.ConversationDto

interface ChatService {
    @GET("chat/v1/conversations")
    suspend fun conversations(): ApiResponseSuccess<List<ConversationDto>>
}