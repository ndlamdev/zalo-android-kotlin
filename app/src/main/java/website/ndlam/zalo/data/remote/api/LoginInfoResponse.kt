package website.ndlam.zalo.data.remote.api

data class LoginInfoResponse(
    val phoneNumber: String,
    val phoneNumberCode: String,
    val token: String
)
