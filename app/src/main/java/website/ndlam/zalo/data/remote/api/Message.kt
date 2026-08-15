package website.ndlam.zalo.data.remote.api

import website.ndlam.zalo.data.enums.ContentMessageType


class Message : BaseEntity() {
    var conversationId: String? = null

    var senderId: String? = null

    var content: String? = null

    var messageType: ContentMessageType = ContentMessageType.TEXT

    var replyToId: String? = null

    var isPinned: Boolean = false
}
