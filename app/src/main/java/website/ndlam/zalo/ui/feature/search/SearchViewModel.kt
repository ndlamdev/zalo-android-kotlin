package website.ndlam.zalo.ui.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import website.ndlam.zalo.data.remote.api.UserInRelationShip
import website.ndlam.zalo.network.RetrofitClientSecured

class SearchViewModel : ViewModel {
    private val _user = MutableStateFlow<UserInRelationShip?>(null)
    val user = _user.asStateFlow()
    private val _textSearch = MutableStateFlow("")

    constructor() {
        viewModelScope.launch {
            _textSearch.debounce(200)
                .collect { data ->
                    try {
                        val response = RetrofitClientSecured.userService.search(data)
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