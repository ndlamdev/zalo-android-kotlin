package website.ndlam.zalo.utils.validation

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber
import website.ndlam.zalo.utils.formater.PhoneNumberFormater


object PhoneNumberValidation {
    private val phoneNumberUtil = PhoneNumberUtil.getInstance()

    fun isValidPhoneNumber(swissNumberStr: String, region: String): Boolean {
        try {
            val swissNumberProto: PhoneNumber? = PhoneNumberFormater.parse(swissNumberStr, region)
            return phoneNumberUtil.isValidNumber(swissNumberProto)
        } catch (e: NumberParseException) {
            System.err.println("NumberParseException was thrown: $e")
            return false
        }
    }

    fun isValidPhoneNumber(swissNumber: Long, countryCode: Int): Boolean {
        try {
            val swissNumberProto: PhoneNumber? = PhoneNumberFormater.parse(swissNumber, countryCode)
            return phoneNumberUtil.isValidNumber(swissNumberProto)
        } catch (e: NumberParseException) {
            System.err.println("NumberParseException was thrown: $e")
            return false
        }
    }
}