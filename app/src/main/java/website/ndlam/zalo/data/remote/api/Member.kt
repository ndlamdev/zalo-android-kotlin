package website.ndlam.zalo.data.remote.api

import website.ndlam.zalo.data.enums.MemberRole
import java.time.LocalDateTime

class Member {
    var id: String? = null

    var phoneNumber: String? = null

    var role: MemberRole = MemberRole.USER

    var joinedAt: LocalDateTime? = null

    var joinedBy: String? = null

    var metadata: ConversationMemberMetadata? = null

    var user: UserInfo? = null
}
