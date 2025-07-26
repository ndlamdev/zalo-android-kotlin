package com.lamnguyen.zalo.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.configs.RetrofitClient
import com.lamnguyen.zalo.dtos.requests.PhoneNumberRequest
import com.lamnguyen.zalo.dtos.responses.ApiResponseSuccess
import com.lamnguyen.zalo.ui.inputpassword.InputPasswordActivity
import com.lamnguyen.zalo.ui.login.viewmodels.PhoneNumberViewModel
import com.lamnguyen.zalo.ui.phonenumbercode.PhoneNumberCodeSelectorActivity
import com.lamnguyen.zalo.ui.phonenumbercode.fragments.InputPhoneNumberFragment
import com.lamnguyen.zalo.utils.helpers.LogHelper
import com.lamnguyen.zalo.utils.helpers.PhoneNumberValidatorHelper
import com.lamnguyen.zalo.utils.helpers.PhoneNumberValidatorHelper.Companion.formatPhoneNumberToNational
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var fgInputPhoneNumber: InputPhoneNumberFragment
    private lateinit var btnContinue: AppCompatButton
    private lateinit var phoneNumberViewModel: PhoneNumberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        phoneNumberViewModel = ViewModelProvider(this)[PhoneNumberViewModel::class.java]
        phoneNumberViewModel.phoneNumberCodeLiveData.value =
            PhoneNumberCodeSelectorActivity.DEFAULT_PHONE_NUMBER_DIAL_CODE

        fgInputPhoneNumber = supportFragmentManager
            .findFragmentById(R.id.fragment_input_phone_number) as InputPhoneNumberFragment

        activityResultLauncher = PhoneNumberCodeSelectorActivity
            .registerForActivityResult(this) { result ->
                if (result != null) {
                    phoneNumberViewModel.phoneNumberCodeLiveData.value = result.dialCode
                }
            }

        btnContinue = findViewById(R.id.button_continue)

        fgInputPhoneNumber.onClickChoicePhoneNumberCode = View.OnClickListener {
            PhoneNumberCodeSelectorActivity.startActivityResult(this, activityResultLauncher)
        }

        findViewById<ImageButton>(R.id.button_back).setOnClickListener {
            finish()
        }

        btnContinue.setOnClickListener {
            checkPhoneNumberExists()
        }

        phoneNumberViewModel.phoneNumberLiveData.observe(this) { data ->
            formatButtonContinue(
                phoneNumberViewModel.phoneNumberCodeLiveData.value.toString(),
                data
            )
        }
        phoneNumberViewModel.phoneNumberCodeLiveData.observe(this) { data ->
            fgInputPhoneNumber.setPhoneNumberCode(data)
            formatButtonContinue(data, phoneNumberViewModel.phoneNumberLiveData.value.toString())
        }
    }

    override fun onResume() {
        fgInputPhoneNumber.addTextWatcher(
            onTextChanged = { text, _, _, _ ->
                phoneNumberViewModel.phoneNumberLiveData.value = text.toString()
            }
        )
        super.onResume()
    }

    private fun checkPhoneNumberExists() {
        val phoneNumberCode = phoneNumberViewModel.phoneNumberCodeLiveData.value.toString()
        val phoneNumber = phoneNumberViewModel.phoneNumberLiveData.value.toString()
        val body = PhoneNumberRequest("${phoneNumberCode}/${phoneNumber}")
        lifecycleScope.launch {
            try {
                RetrofitClient.authService
                    .checkPhoneNumber(body)
                checkPhoneNumberSuccess(phoneNumberCode, phoneNumber)
            } catch (e: Exception) {
                LogHelper.errorWithClassName(
                    this@LoginActivity,
                    e.message.toString(),
                    e
                )
            }
        }
    }

    private fun checkPhoneNumberSuccess(phoneNumberCode: String, phoneNumber: String) {
        this@LoginActivity.startActivity(
            Intent(
                this@LoginActivity,
                InputPasswordActivity::class.java
            ).apply {
                val phoneNumber = formatPhoneNumberToNational(
                    phoneNumberCode,
                    phoneNumber
                )
                putExtra(InputPasswordActivity.ARG_PHONE_NUMBER, phoneNumber)
                putExtra(InputPasswordActivity.ARG_PHONE_NUMBER_CODE, phoneNumberCode)
            }
        )
    }

    private fun formatButtonContinue(phoneNumberCode: String, phoneNumber: String) {
        val valid = PhoneNumberValidatorHelper.validate(
            phoneNumberCode,
            phoneNumber
        )
        btnContinue.isEnabled = valid
        btnContinue.setTextColor(
            if (valid) resources.getColor(R.color.white, null)
            else resources.getColor(R.color.gray_500, null)
        )
    }
}