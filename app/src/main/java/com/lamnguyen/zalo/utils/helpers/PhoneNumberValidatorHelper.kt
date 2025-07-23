package com.lamnguyen.zalo.utils.helpers

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber

class PhoneNumberValidatorHelper {
    companion object {
        private val phoneNumber = Phonenumber.PhoneNumber()

        @JvmStatic
        fun validate(countryCode: String, nationalNumber: String): Boolean {
            val nationalNumberFormat = nationalNumber.replace(Regex("\\D"), "")
            if (nationalNumberFormat.isEmpty() || countryCode.isEmpty()) return false
            phoneNumber.countryCode = countryCode.substring(1).toInt()
            phoneNumber.nationalNumber = nationalNumberFormat.toLong()
            return PhoneNumberUtil.getInstance().isPossibleNumberForType(
                phoneNumber,
                PhoneNumberUtil.PhoneNumberType.MOBILE
            )
        }
    }
}