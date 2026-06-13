package website.ndlam.zalo.data.remote.api

data class LoginInfoRequest(
    val phoneNumber: String,
    val password: String
)