package website.ndlam.zalo.ui.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import website.ndlam.zalo.core.stomp.Stomp
import website.ndlam.zalo.core.stomp.StompClient
import website.ndlam.zalo.core.stomp.dto.StompMessage
import website.ndlam.zalo.domain.repository.IAuthTokenRepository

class StompMessageViewModel(val authTokenResponse: IAuthTokenRepository) : ViewModel() {
    private var stomp: StompClient? = null

    fun connect(uri: String, headers: Map<String, String>? = mapOf()) {
        viewModelScope.launch {
            val token = authTokenResponse.getAccessToken()
            val currentHeaders = mutableMapOf<String, String>()
            if (headers != null) currentHeaders.putAll(currentHeaders)
            currentHeaders["authorization"] = "Bearer $token"
            stomp = Stomp.over(Stomp.ConnectionProvider.OKHTTP, uri, currentHeaders, viewModelScope)
            stomp?.connect()
        }
    }

    fun subscribe(path: String, callback: (message: StompMessage?) -> Unit) {
        viewModelScope.launch {
            stomp?.topic(path)
                ?.collect { callback(it) }
        }
    }
}