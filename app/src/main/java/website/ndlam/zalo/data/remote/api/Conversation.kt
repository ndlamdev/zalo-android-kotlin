package website.ndlam.zalo.data.remote.api

import website.ndlam.zalo.data.enums.ConversationType
import java.time.LocalDateTime

class ConversationDto {
    var softId: String? = null

    var admin: String? = null

    var type: ConversationType? = ConversationType.PRIVATE

    private var title: String? = null

    var avatarUrl: String? = null

    var theme: String? = null

    var pinned: Boolean = false

    var muted: Boolean = false

    var archived: Boolean = false

    var totalMessageUnread: Int = 0

    var lastMessage: Message? = null

    var viewerId: String? = null

    var members: MutableList<Member> = mutableListOf()

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getTitle(): String {
        if (!title.isNullOrBlank()) return title!!

        if (type === ConversationType.PRIVATE) {
            return members.firstOrNull { it.id != viewerId }?.user?.fullName
                ?: "Conversation title"
        }

        val allNameMember =
            members.filter { it.id != viewerId }.mapNotNull { it.user?.fullName }
                .joinToString(", ").substring(0, 150)

        return "Bạn, $allNameMember"
    }
}
