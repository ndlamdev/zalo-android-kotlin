package website.ndlam.zalo.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PasswordTextFieldViewModel : ViewModel() {
    private val _password = MutableStateFlow("")

    val password: StateFlow<String> = _password.asStateFlow()

    private val _focus = MutableStateFlow(false)

    val focusState: StateFlow<Boolean> = _focus.asStateFlow()

    private val _hidden = MutableStateFlow(true)
    val hidden: StateFlow<Boolean>  = _hidden.asStateFlow()

    fun updatePassword(newPhoneNumber: String) {
        _password.value = newPhoneNumber
    }

    fun updateFocus(focus: Boolean) {
        _focus.value = focus
    }

    fun show() {
        _hidden.value = false
    }

    fun hide() {
        _hidden.value = true
    }
}
