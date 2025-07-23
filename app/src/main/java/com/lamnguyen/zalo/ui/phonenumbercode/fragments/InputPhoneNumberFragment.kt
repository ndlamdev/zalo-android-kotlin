package com.lamnguyen.zalo.ui.phonenumbercode.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.edit
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.phonenumbercode.PhoneNumberCodeSelectorActivity
import com.lamnguyen.zalo.utils.enums.SharedPreferenceKeys

class InputPhoneNumberFragment : Fragment() {
    private lateinit var textPhoneNumberCode: TextView
    private lateinit var editPhoneNumber: EditText
    private lateinit var layoutInputPhoneNumber: LinearLayout
    private lateinit var layoutPhoneNumberCode: ConstraintLayout
    var onChoicePhoneNumberCode: View.OnClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_input_phone_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutInputPhoneNumber = view.findViewById(R.id.layout_input_phone_number)
        layoutPhoneNumberCode = view.findViewById(R.id.layout_phone_number_code)

        textPhoneNumberCode = view.findViewById(R.id.text_phone_number_code)
        textPhoneNumberCode.text = getDialCode()

        editPhoneNumber = view.findViewById(R.id.edit_phone_number)
        editPhoneNumber.setOnFocusChangeListener { v, hasFocus ->
            layoutInputPhoneNumber.isSelected = hasFocus
            layoutPhoneNumberCode.isSelected = hasFocus
        }


        view.findViewById<ConstraintLayout>(R.id.layout_phone_number_code)
            .setOnClickListener(onChoicePhoneNumberCode)
    }

    private fun getDialCode(): String {
        val shared = view?.context?.getSharedPreferences(
            SharedPreferenceKeys.AUTHENTICATION.name,
            MODE_PRIVATE
        )
        val dialCode =
            shared?.getString(
                PhoneNumberCodeSelectorActivity.PARAM_ARG_PHONE_NUMBER_DIAL_CODE,
                PhoneNumberCodeSelectorActivity.DEFAULT_PHONE_NUMBER_DIAL_CODE
            )

        if (dialCode.isNullOrEmpty()) {
            shared?.edit(true) {
                putString(
                    PhoneNumberCodeSelectorActivity.PARAM_ARG_PHONE_NUMBER_DIAL_CODE,
                    PhoneNumberCodeSelectorActivity.DEFAULT_PHONE_NUMBER_DIAL_CODE
                )
                putString(
                    PhoneNumberCodeSelectorActivity.PARAM_ARG_PHONE_NUMBER_CODE,
                    PhoneNumberCodeSelectorActivity.DEFAULT_PHONE_NUMBER_CODE
                )
                putString(
                    PhoneNumberCodeSelectorActivity.PARAM_ARG_PHONE_NUMBER_COUNTRY,
                    PhoneNumberCodeSelectorActivity.DEFAULT_PHONE_NUMBER_COUNTRY
                )
            }
        }

        return dialCode ?: PhoneNumberCodeSelectorActivity.DEFAULT_PHONE_NUMBER_DIAL_CODE
    }

    fun setPhoneNumberCode(dialCode: String?) {
        textPhoneNumberCode.text = dialCode
    }
}