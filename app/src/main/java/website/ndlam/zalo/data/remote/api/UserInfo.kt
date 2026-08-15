package website.ndlam.zalo.data.remote.api

import website.ndlam.zalo.data.enums.RelationShipStatus
import java.time.LocalDate

data class UserInfo(
    var phoneNumber: String,
    var fullName: String? = null,
    var birthDate: LocalDate? = null,
    var avatar: String? = null,
    var email: String? = null,
    var displayName: String? = null,
    var relationShipStatus: RelationShipStatus = RelationShipStatus.SELF
)