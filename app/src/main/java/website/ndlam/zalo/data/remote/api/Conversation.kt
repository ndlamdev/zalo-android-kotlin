package website.ndlam.zalo.data.remote.api

import website.ndlam.zalo.data.enums.ConversationType
import java.time.LocalDateTime

class ConversationDto  {
    var softId: String? = null

    var admin: String? = null

    var type: ConversationType? = ConversationType.PRIVATE

    var title: String? = null

    var avatarUrl: String? = null

    var theme: String? = null

    var lastMessageId: String? = null

    var lastMessageAt: LocalDateTime? = null

    var isMuted: Boolean = false
    var pinned: Boolean = false
    var members: MutableList<Member> = mutableListOf()
    var lastMessage: Message? = null
    var pinMessages: MutableList<Message> = mutableListOf()
}
