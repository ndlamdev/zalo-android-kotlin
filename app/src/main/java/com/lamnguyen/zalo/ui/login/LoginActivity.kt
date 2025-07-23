package com.lamnguyen.zalo.ui.login

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.phonenumbercode.PhoneNumberCodeSelectorActivity
import com.lamnguyen.zalo.ui.phonenumbercode.fragments.InputPhoneNumberFragment
import com.lamnguyen.zalo.utils.helpers.PhoneNumberValidatorHelper

class LoginActivity : AppCompatActivity() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var fgInputPhoneNumber: InputPhoneNumberFragment
    private lateinit var btnContinue: AppCompatButton
    private var phoneNumberCode: String? =
        PhoneNumberCodeSelectorActivity.DEFAULT_PHONE_NUMBER_DIAL_CODE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        fgInputPhoneNumber = supportFragmentManager
            .findFragmentById(R.id.fragment_input_phone_number) as InputPhoneNumberFragment

        activityResultLauncher = PhoneNumberCodeSelectorActivity
            .registerForActivityResult(this) { result ->
                if (result != null) {
                    phoneNumberCode = result.dialCode
                    fgInputPhoneNumber.setPhoneNumberCode(result.dialCode)
                }
            }

        btnContinue = findViewById(R.id.button_continue)

        fgInputPhoneNumber.onClickChoicePhoneNumberCode = View.OnClickListener {
            PhoneNumberCodeSelectorActivity.startActivityResult(this, activityResultLauncher)
        }

        findViewById<ImageButton>(R.id.button_back).setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        fgInputPhoneNumber.addTextWatcher(
            onTextChanged = { text, _, _, _ ->
                val valid = PhoneNumberValidatorHelper.validate(
                    phoneNumberCode
                        ?: PhoneNumberCodeSelectorActivity.DEFAULT_PHONE_NUMBER_DIAL_CODE,
                    text.toString()
                )
                btnContinue.isEnabled = valid
                btnContinue.setTextColor(
                    if (valid) resources.getColor(R.color.white, null)
                    else resources.getColor(R.color.gray_500, null)
                )
            }
        )
        super.onResume()
    }
}