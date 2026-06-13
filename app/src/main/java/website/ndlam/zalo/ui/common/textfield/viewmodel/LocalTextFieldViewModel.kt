package website.ndlam.zalo.ui.common.textfield.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class LocalTextFieldViewModel : ViewModel() {
    private val _value = MutableStateFlow("")
    val value = _value.asStateFlow()
    private val focus = MutableStateFlow(false)
    val focusState = focus.asStateFlow()


    open fun setValue(text: String) {
        this._value.value = text
    }

    fun setFocus(focus: Boolean) {
        this.focus.value = focus
    }
}