package website.ndlam.zalo.data.remote.api

import java.time.LocalDateTime

class ConversationMemberMetadata  {
    var deletedConversationAt: LocalDateTime? = LocalDateTime.now()

    var lastReadMessageAt: LocalDateTime? = LocalDateTime.now()

    var pinned: Boolean = false

    var muted: Boolean = false

    var archived: Boolean = false
}
