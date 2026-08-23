package website.ndlam.zalo.core.util.formater

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber

object PhoneNumberFormater {
    private val phoneNumberUtil = PhoneNumberUtil.getInstance()

    /**
     * Produces "+41 44 668 18 00"
     */
    fun internationalFormat(swissNumberStr: String, region: String): String {
        val swissNumberProto = parse(swissNumberStr, region)
        return phoneNumberUtil.format(
            swissNumberProto,
            PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL
        )
    }

    /**
     * Produces "044 668 18 00"
     */
    fun nationalFormat(swissNumberStr: String, region: String): String {
        val swissNumberProto = parse(swissNumberStr, region)
        return phoneNumberUtil.format(
            swissNumberProto,
            PhoneNumberUtil.PhoneNumberFormat.NATIONAL
        )
    }

    /**
     * Produces "044 668 18 00"
     */
    fun e164Format(swissNumberStr: String, region: String): String {
        val swissNumberProto = parse(swissNumberStr, region)
        return phoneNumberUtil.format(
            swissNumberProto,
            PhoneNumberUtil.PhoneNumberFormat.E164
        )
    }

    /**
     * Produces "+41 44 668 18 00"
     */
    fun internationalFormat(swissNumber: Long, countryCode: Int): String {
        val swissNumberProto = parse(swissNumber, countryCode)
        return phoneNumberUtil.format(
            swissNumberProto,
            PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL
        )
    }

    /**
     * Produces "044 668 18 00"
     */
    fun nationalFormat(swissNumber: Long, countryCode: Int): String {
        val swissNumberProto = parse(swissNumber, countryCode)
        return phoneNumberUtil.format(
            swissNumberProto,
            PhoneNumberUtil.PhoneNumberFormat.NATIONAL
        )
    }

    /**
     * Produces "+41446681800"
     */
    fun e164Format(swissNumber: Long, countryCode: Int): String {
        val swissNumberProto = parse(swissNumber, countryCode)
        return phoneNumberUtil.format(
            swissNumberProto,
            PhoneNumberUtil.PhoneNumberFormat.E164
        )
    }

    fun parse(swissNumberStr: String, region: String): Phonenumber.PhoneNumber {
        return phoneNumberUtil.parse(swissNumberStr, region)
    }

    fun parse(swissNumber: Long, countryCode: Int): Phonenumber.PhoneNumber {
        return Phonenumber.PhoneNumber().setCountryCode(countryCode).setNationalNumber(swissNumber)
    }
}