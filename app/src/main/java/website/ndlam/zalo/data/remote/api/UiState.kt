package website.ndlam.zalo.data.remote.api

sealed interface UiState<T> {
    class Loading<T> : UiState<T>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}