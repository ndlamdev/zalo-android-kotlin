package website.ndlam.zalo.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import website.ndlam.zalo.utils.validation.PhoneNumberValidation

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
        _regionCode.value = regionCode
    }
}
