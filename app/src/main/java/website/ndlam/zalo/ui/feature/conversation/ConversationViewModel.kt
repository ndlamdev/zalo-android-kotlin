package website.ndlam.zalo.ui.feature.conversation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import website.ndlam.zalo.data.remote.api.ApiState
import website.ndlam.zalo.data.remote.api.ConversationDto
import website.ndlam.zalo.network.RetrofitClientSecured
import website.ndlam.zalo.network.helper.getResponseError

class ConversationViewModel : ViewModel() {
    private val _conversations = MutableStateFlow<ApiState<List<ConversationDto>>?>(null)
    val conversation = _conversations.asStateFlow()

    fun loadConversations() {
        viewModelScope.launch {
            try {
                _conversations.value = ApiState.Loading()
                val response = RetrofitClientSecured.chatService.conversations()
                _conversations.value = ApiState.Success(response.data)
            } catch (e: HttpException) {
                val response = e.getResponseError()
                _conversations.value =
                    ApiState.Error(response?.detail ?: response?.error ?: e.message())
            } catch (e: Exception) {
                _conversations.value =
                    ApiState.Error(e.message ?: "Something wrong!")
            }
        }
    }
}
