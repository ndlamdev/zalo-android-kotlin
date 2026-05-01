package website.ndlam.zalo.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PhoneNumberViewModel : ViewModel() {
    private val _phoneNumber = MutableStateFlow("")

    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()
    private val _countryCode = MutableStateFlow("+84")

    val countryCode: StateFlow<String> = _countryCode.asStateFlow()
    fun updatePhoneNumber(newPhoneNumber: String) {
        _phoneNumber.value = newPhoneNumber
    }
}
