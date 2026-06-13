package website.ndlam.zalo.data.remote.api

sealed interface ApiState<T> {
    class Loading<T> : ApiState<T>
    data class Success<T>(val data: T) : ApiState<T>
    class SuccessNotResponse<T> : ApiState<T>
    data class Error<T>(val message: String) : ApiState<T>
}