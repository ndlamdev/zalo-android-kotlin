package com.lamnguyen.zalo.utils.helpers

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import com.lamnguyen.zalo.utils.helpers.PhoneNumberValidatorHelper.Companion.phoneNumber

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

        @JvmStatic
        fun formatPhoneNumberToNational(countryCode: String, nationalNumber: String): String? {
            val nationalNumberFormat = nationalNumber.replace(Regex("\\D"), "")
            if (nationalNumberFormat.isEmpty() || countryCode.isEmpty()) return null
            phoneNumber.countryCode = countryCode.substring(1).toInt()
            phoneNumber.nationalNumber = nationalNumberFormat.toLong()
            return PhoneNumberUtil.getInstance()
                .format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL)
        }
    }
}