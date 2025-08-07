package com.lamnguyen.zalo.domain.dtos

data class User(
    var phoneNumber: String? = null,
    var fullName: String? = null,
//    var birthDate: LocalDate? = null,
    var avatar: String? = null,
    var email: String? = null,
) {
}