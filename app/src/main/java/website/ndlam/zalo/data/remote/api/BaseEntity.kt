package website.ndlam.zalo.data.remote.api

import java.time.LocalDateTime

open class BaseEntity{
     var id: String? = null

    var createdAt: LocalDateTime? = null

    var updatedAt: LocalDateTime? = null

    var createdBy: String? = null

    var updatedBy: String? = null

    var isDeleted: Boolean = false
}
