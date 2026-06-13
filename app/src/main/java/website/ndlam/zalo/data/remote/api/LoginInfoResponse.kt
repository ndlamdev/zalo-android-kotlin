package website.ndlam.zalo.data.remote.api

data class LoginInfoResponse(
    val phoneNumber: String,
    val numberPhoneCode: String,
    val accessToken: String
)
