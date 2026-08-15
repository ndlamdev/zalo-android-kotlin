package website.ndlam.zalo.data.remote.api

import website.ndlam.zalo.data.enums.MemberRole
import java.time.LocalDateTime

class Member : BaseEntity() {
    var conversationId: String? = null

    var userId: String? = null

    var role: MemberRole = MemberRole.USER

    var joinedAt: LocalDateTime? = null

    var joinedBy: String? = null

    var muted: Boolean = false

    var active: Boolean = true

    @Transient
    var metadata: ConversationMemberMetadata? = null
}
