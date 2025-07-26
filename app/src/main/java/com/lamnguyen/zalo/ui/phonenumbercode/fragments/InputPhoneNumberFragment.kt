package com.lamnguyen.zalo.ui.phonenumbercode.fragments

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.edit
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.phonenumbercode.PhoneNumberCodeSelectorActivity
import com.lamnguyen.zalo.utils.enums.SharedPreferenceNames
import androidx.core.widget.addTextChangedListener

class InputPhoneNumberFragment : Fragment() {
    private lateinit var textPhoneNumberCode: TextView
    private lateinit var editPhoneNumber: EditText
    private lateinit var layoutInputPhoneNumber: LinearLayout
    private lateinit var layoutPhoneNumberCode: ConstraintLayout
    var onClickChoicePhoneNumberCode: View.OnClickListener? = null

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

        view.findViewById<ConstraintLayout>(R.id.layout_phone_number_code)
            .setOnClickListener(onClickChoicePhoneNumberCode)


        val layoutButtonClean = view.findViewById<FrameLayout>(R.id.layout_button_clean_search)

        layoutButtonClean.setOnClickListener {
            editPhoneNumber.text.clear()
        }

        editPhoneNumber.filters = arrayOf(
            InputFilter { source, _, _, _, _, _ ->
                source.replace(Regex("[\\D\\s]"), "")
            },
            InputFilter.LengthFilter(15)
        )

        editPhoneNumber.setOnFocusChangeListener { v, hasFocus ->
            layoutInputPhoneNumber.isSelected = hasFocus
            layoutPhoneNumberCode.isSelected = hasFocus
            layoutButtonClean.visibility =
                if (hasFocus && editPhoneNumber.text.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        }

        editPhoneNumber.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                layoutButtonClean.visibility =
                    if (text?.isNotEmpty() == true) View.VISIBLE else View.INVISIBLE
            }
        )
        layoutInputPhoneNumber.setOnClickListener {
            editPhoneNumber.requestFocus()
        }
    }

    private fun getDialCode(): String {
        val shared = view?.context?.getSharedPreferences(
            SharedPreferenceNames.AUTHENTICATION.name,
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

    fun addTextWatcher(
        beforeTextChanged:
            (text: CharSequence?, start: Int, count: Int, after: Int) -> Unit =
            { _, _, _, _ -> },
        onTextChanged: (text: CharSequence?, start: Int, before: Int, count: Int) -> Unit =
            { _, _, _, _ -> },
        afterTextChanged: (text: Editable?) -> Unit = {}
    ) {
        editPhoneNumber.addTextChangedListener(
            beforeTextChanged,
            onTextChanged,
            afterTextChanged
        )
    }
}