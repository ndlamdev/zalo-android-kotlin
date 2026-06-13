package website.ndlam.zalo.data.remote.api

data class ApiResponse<T>(val code: Int, val message: String, val data: T)
