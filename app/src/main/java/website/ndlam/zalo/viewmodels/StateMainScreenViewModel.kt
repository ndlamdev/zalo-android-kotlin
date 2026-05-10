package website.ndlam.zalo.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import website.ndlam.zalo.utils.enums.ScreenOnMainScreen

class StateMainScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScreenOnMainScreen.MESSAGE)
    val state = _state.asStateFlow()

    fun setState(state: ScreenOnMainScreen) {
        this._state.value = state
    }
}