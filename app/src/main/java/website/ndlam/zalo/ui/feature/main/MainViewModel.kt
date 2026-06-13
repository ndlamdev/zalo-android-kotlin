package website.ndlam.zalo.ui.feature.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import website.ndlam.zalo.data.remote.api.UiState
import website.ndlam.zalo.data.remote.api.UserInfo
import website.ndlam.zalo.domain.repository.IAuthTokenRepository

class MainViewModel(val authTokenRepository: IAuthTokenRepository) :
    ViewModel() {
    private val _user = MutableStateFlow<UiState<UserInfo>>(UiState.Loading())
    val user: StateFlow<UiState<UserInfo>> = _user.asStateFlow()
}