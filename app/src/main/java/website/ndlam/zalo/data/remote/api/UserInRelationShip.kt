package website.ndlam.zalo.data.remote.api

import website.ndlam.zalo.data.enums.RelationShipStatus
import java.time.LocalDate

class UserInRelationShip(
    phoneNumber: String,
    fullName: String?,
    birthDate: LocalDate,
    avatar: String,
    email: String,
    var displayName: String? = null,
    var relationShipStatus: RelationShipStatus = RelationShipStatus.SELF
) : UserInfo(phoneNumber, fullName, birthDate, avatar, email)