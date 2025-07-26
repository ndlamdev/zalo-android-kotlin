package com.lamnguyen.zalo.ui.inputpassword

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.configs.RetrofitClient
import com.lamnguyen.zalo.dtos.requests.LoginRequest
import com.lamnguyen.zalo.dtos.responses.LoginResponse
import com.lamnguyen.zalo.ui.inputpassword.viewmodels.LoginViewModel
import com.lamnguyen.zalo.ui.main.MainActivity
import com.lamnguyen.zalo.utils.enums.SharedPreferenceNames
import com.lamnguyen.zalo.utils.helpers.LogHelper
import com.lamnguyen.zalo.utils.helpers.TokenHelper
import kotlinx.coroutines.launch

class InputPasswordActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    lateinit var layoutInputPassword: LinearLayout
    lateinit var editPassword: EditText
    lateinit var btnShowOrHiddenPassword: ImageButton
    lateinit var btnContinue: AppCompatButton
    lateinit var btnBack: ImageButton
    lateinit var txtPhoneNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_password)

        layoutInputPassword = findViewById(R.id.layout_input_password)
        editPassword = findViewById<EditText>(R.id.edit_password).apply {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        btnShowOrHiddenPassword = findViewById(R.id.button_show_or_hidden_password)
        btnContinue = findViewById(R.id.button_continue)
        btnBack = findViewById(R.id.button_back)
        txtPhoneNumber = findViewById(R.id.text_phone_number)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java].apply {
            phoneNumberCodeLiveData.value = intent.extras?.getString(ARG_PHONE_NUMBER_CODE, "")
            phoneNumberLiveData.value = intent.extras?.getString(ARG_PHONE_NUMBER, "")
        }

        txtPhoneNumber.text = loginViewModel.phoneNumberLiveData.value.toString()

        setUpEvent()
    }

    private fun setUpEvent() {
        editPassword.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            layoutInputPassword.isSelected = hasFocus
        }

        editPassword.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                loginViewModel.passwordLiveData.value = text.toString()
                btnContinue.isEnabled = text?.isNotEmpty() == true
                btnContinue.setTextColor(
                    if (text?.isNotEmpty() == true) resources.getColor(R.color.white, null)
                    else resources.getColor(R.color.gray_500, null)
                )
            })

        btnShowOrHiddenPassword.setOnClickListener {
            showHiddenPassword()
        }


        btnBack.setOnClickListener {
            finish()
        }

        btnContinue.setOnClickListener {
            login()
        }

        layoutInputPassword.setOnClickListener {
            editPassword.requestFocus()
        }
    }

    private fun showHiddenPassword() {
        if (editPassword.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            btnShowOrHiddenPassword.setImageResource(R.drawable.ic_eye_closed)
            editPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            btnShowOrHiddenPassword.setImageResource(R.drawable.ic_eye)
            editPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        editPassword.setSelection(editPassword.text.length)
    }

    private fun login() {
        lifecycleScope.launch {
            val request = LoginRequest(
                "${loginViewModel.phoneNumberCodeLiveData.value}/${loginViewModel.phoneNumberLiveData.value}",
                loginViewModel.passwordLiveData.value.toString()
            )
            try {
                val result = RetrofitClient.authService.login(request)
                result.data?.let { saveDataLogin(it) }

                startActivity(Intent(this@InputPasswordActivity, MainActivity::class.java))
                finish()
            } catch (e: Exception) {
                LogHelper.errorWithClassName(
                    this@InputPasswordActivity,
                    e.message.toString(),
                    e
                )
            }
        }
    }


    private fun saveDataLogin(response: LoginResponse) {
        TokenHelper.saveAccessToken(
            response.accessToken,
            this@InputPasswordActivity
        )

        getSharedPreferences(
            SharedPreferenceNames.AUTHENTICATION.name,
            MODE_PRIVATE
        ).edit(true) {
            putString(
                SharedPreferenceNames.SharedPreferenceKeys.AUTHENTICATION_PHONE_NUMBER_CODE.name,
                response.phoneNumberCode
            )
            putString(
                SharedPreferenceNames.SharedPreferenceKeys.AUTHENTICATION_PHONE_NUMBER.name,
                response.phoneNumber
            )
            putString(
                SharedPreferenceNames.SharedPreferenceKeys.AUTHENTICATION_PHONE_NUMBER_AND_CODE.name,
                "${response.phoneNumberCode}/${response.phoneNumber}"
            )
        }
    }


    companion object {
        const val ARG_PHONE_NUMBER = "phone_number"
        const val ARG_PHONE_NUMBER_CODE = "phone_number_code"
    }
}