package com.lamnguyen.zalo.ui.phonenumbercode

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.phonenumbercode.viewmodels.PhoneNumberViewModel
import com.lamnguyen.zalo.utils.adapters.PhoneNumberCodeRecyclerViewAdapter
import com.lamnguyen.zalo.utils.adapters.PhoneNumberCodeRecyclerViewAdapter.PhoneNumberCode
import java.util.function.Consumer

class PhoneNumberCodeSelectorActivity : AppCompatActivity() {
    private lateinit var phoneNumberViewModel: PhoneNumberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number_code_selector)

        phoneNumberViewModel = ViewModelProvider(this)[PhoneNumberViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_phone_number_code)
        val adapter = PhoneNumberCodeRecyclerViewAdapter(
            this,
            phoneNumberViewModel,
            this::onPhoneNumberSelectedHandler
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        findViewById<ImageButton>(R.id.image_button_back).setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    fun onPhoneNumberSelectedHandler(phoneNumberCode: PhoneNumberCode) {
        val resultIntent = Intent().apply {
            putExtra(PARAM_ARG_PHONE_NUMBER_CODE, phoneNumberCode.code)
            putExtra(PARAM_ARG_PHONE_NUMBER_DIAL_CODE, phoneNumberCode.dialCode)
            putExtra(PARAM_ARG_PHONE_NUMBER_COUNTRY, phoneNumberCode.name)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    companion object {
        const val PARAM_ARG_PHONE_NUMBER_CODE = "code"
        const val PARAM_ARG_PHONE_NUMBER_COUNTRY = "country"
        const val PARAM_ARG_PHONE_NUMBER_DIAL_CODE = "dial_code"
        const val DEFAULT_PHONE_NUMBER_CODE = "VN"
        const val DEFAULT_PHONE_NUMBER_COUNTRY = "Vietnam"
        const val DEFAULT_PHONE_NUMBER_DIAL_CODE = "+84"

        fun registerForActivityResult(
            activity: AppCompatActivity,
            onResult: Consumer<PhoneNumberCode?>
        ): ActivityResultLauncher<Intent> {
            val activityResultLauncher =
                activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        val data = result.data?.let {
                            val code = it.getStringExtra(PARAM_ARG_PHONE_NUMBER_CODE)
                            val dialCode = it.getStringExtra(PARAM_ARG_PHONE_NUMBER_DIAL_CODE)
                            val country = it.getStringExtra(PARAM_ARG_PHONE_NUMBER_COUNTRY)
                            PhoneNumberCode().apply {
                                name = country ?: ""
                                this.code = code
                                this.dialCode = dialCode
                            }
                        }

                        onResult.accept(data)
                    }
                }

            return activityResultLauncher
        }

        fun startActivityResult(
            context: Context,
            activityResultLauncher: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, PhoneNumberCodeSelectorActivity::class.java)
            activityResultLauncher.launch(intent)
        }
    }
}