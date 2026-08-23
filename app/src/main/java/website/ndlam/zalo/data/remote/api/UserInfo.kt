package website.ndlam.zalo.data.remote.api

import java.time.LocalDate

open class UserInfo(
    var phoneNumber: String,
    var fullName: String? = null,
    var birthDate: LocalDate? = null,
    var avatar: String? = null,
    var email: String? = null,
)