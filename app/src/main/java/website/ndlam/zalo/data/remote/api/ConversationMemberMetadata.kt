package website.ndlam.zalo.data.remote.api

import java.time.LocalDateTime

class ConversationMemberMetadata : BaseEntity() {
    var conversationId: String? = null

    var memberId: String? = null

    var lastReadMessageAt: LocalDateTime? = null

    var pinned: Boolean = false

    var muted: Boolean = false
    var archived: Boolean = false
}
