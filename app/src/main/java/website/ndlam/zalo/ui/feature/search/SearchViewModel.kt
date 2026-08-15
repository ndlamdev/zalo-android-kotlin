package website.ndlam.zalo.ui.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import website.ndlam.zalo.data.remote.api.UserInfo
import website.ndlam.zalo.domain.repository.IAuthTokenRepository
import website.ndlam.zalo.network.RetrofitClient

class SearchViewModel() : ViewModel() {
    private val _user = MutableStateFlow<UserInfo?>(null)
    val user = _user.asStateFlow()
    private val _textSearch = MutableStateFlow("")

    constructor(authTokenRepository: IAuthTokenRepository) : this() {
        viewModelScope.launch {
            val token = authTokenRepository.getAccessToken()

            _textSearch.debounce(200)
                .collect { data ->
                    try {
                        val response = RetrofitClient.userService(token)
                            .search(data)
                        _user.value = response.data
                    } catch (_: Exception) {
                    }
                }
        }
    }

    fun search(it: String) {
        _textSearch.value = it
    }
}