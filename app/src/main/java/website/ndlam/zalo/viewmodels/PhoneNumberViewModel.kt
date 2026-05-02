package website.ndlam.zalo.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import website.ndlam.zalo.utils.validation.PhoneNumberValidation

class PhoneNumberViewModel : ViewModel() {
    private val _phoneNumber = MutableStateFlow("")

    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()
    private val _countryCode = MutableStateFlow("+84")

    val countryCode: StateFlow<String> = _countryCode.asStateFlow()
    private val _focus = MutableStateFlow(false)

    val focusState: StateFlow<Boolean> = _focus.asStateFlow()

    private val _valid = MutableStateFlow(false)
    val valid: StateFlow<Boolean> = _valid.asStateFlow()

    fun updatePhoneNumber(newPhoneNumber: String) {
        try {
            _valid.value = PhoneNumberValidation.isValidPhoneNumber(
                newPhoneNumber.toLong(),
                _countryCode.value.replace("+", "").toInt()
            )
        } catch (_: Exception) {

        }
        _phoneNumber.value = newPhoneNumber
    }

    fun updateFocus(focus: Boolean) {
        _focus.value = focus
    }
}
