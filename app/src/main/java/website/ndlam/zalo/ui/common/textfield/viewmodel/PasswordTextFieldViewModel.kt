package website.ndlam.zalo.ui.common.textfield.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PasswordTextFieldViewModel : LocalTextFieldViewModel() {
    private val _hidden = MutableStateFlow(true)
    val hidden: StateFlow<Boolean> = _hidden.asStateFlow()


    fun show() {
        _hidden.value = false
    }

    fun hide() {
        _hidden.value = true
    }

}
