package website.ndlam.zalo.core.util.enums

sealed interface ApiCallingStatus<out T> {
    object Loading : ApiCallingStatus<Nothing>

    data class Success<T>(val data: T) : ApiCallingStatus<T>

    data class Error(val message: String) : ApiCallingStatus<Nothing>
}