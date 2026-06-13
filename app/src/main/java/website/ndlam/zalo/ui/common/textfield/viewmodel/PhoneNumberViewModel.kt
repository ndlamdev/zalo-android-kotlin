package website.ndlam.zalo.ui.common.textfield.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import website.ndlam.zalo.core.util.validation.PhoneNumberValidation

class PhoneNumberViewModel : LocalTextFieldViewModel() {
    private val _regionCode = MutableStateFlow("+84")

    val regionCode: StateFlow<String> = _regionCode.asStateFlow()

    private val _valid = MutableStateFlow(false)
    val valid: StateFlow<Boolean> = _valid.asStateFlow()

    override fun setValue(text: String) {
        try {
            _valid.value = PhoneNumberValidation.isValidPhoneNumber(
                text.toLong(),
                _regionCode.value.replace("+", "").toInt()
            )
        } catch (_: Exception) {

        }
        super.setValue(text)
    }

    fun setRegionCode(regionCode: String) {
        try {
            _valid.value = PhoneNumberValidation.isValidPhoneNumber(
                this.value.value.toLong(),
                regionCode.replace("+", "").toInt()
            )
        } catch (_: Exception) {

        }

        _regionCode.value = regionCode
    }
}
