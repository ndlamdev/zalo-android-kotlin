package com.lamnguyen.zalo.utils.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.edit
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.phonenumbercode.PhoneNumberCodeSelectorActivity
import com.lamnguyen.zalo.ui.phonenumbercode.PhoneNumberCodeSelectorActivity.Companion.PARAM_ARG_PHONE_NUMBER_CODE
import com.lamnguyen.zalo.ui.phonenumbercode.PhoneNumberCodeSelectorActivity.Companion.PARAM_ARG_PHONE_NUMBER_COUNTRY
import com.lamnguyen.zalo.ui.phonenumbercode.PhoneNumberCodeSelectorActivity.Companion.PARAM_ARG_PHONE_NUMBER_DIAL_CODE
import com.lamnguyen.zalo.ui.phonenumbercode.viewmodels.PhoneNumberViewModel
import com.lamnguyen.zalo.utils.enums.SharedPreferenceKeys
import java.util.function.Consumer

@SuppressLint("NotifyDataSetChanged")
class PhoneNumberCodeRecyclerViewAdapter(
    activity: PhoneNumberCodeSelectorActivity,
    phoneNumberViewModel: PhoneNumberViewModel,
    private val onPhoneNumberCodeSelected: Consumer<PhoneNumberCode>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val showingData: MutableList<PhoneNumberCode> = mutableListOf()
    private var previousChar = ' '
    private val currentPhoneNumberCode: String?
    private val originData: List<PhoneNumberCode>

    init {
        originData = ObjectMapper().readValue(
            activity.assets.open("CountryCodes.json"),
            object : TypeReference<MutableList<PhoneNumberCode>>() {}
        )

        formatData(originData)

        val shared = activity.getSharedPreferences(
            SharedPreferenceKeys.AUTHENTICATION.name,
            Context.MODE_PRIVATE
        )
        currentPhoneNumberCode = shared
            .getString(PARAM_ARG_PHONE_NUMBER_CODE, null)

        phoneNumberViewModel.textSearchLiveData.observe(activity) { textSearch ->
            if(textSearch.isEmpty())formatData(originData)
            else formatData(originData.filter { it.name.lowercase().contains(textSearch.lowercase()) })
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_CONTENT)
            return PhoneNumberCodeViewHoler(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.fragment_phone_number_code_item, parent, false)
            )

        return PhoneNumberCodeTitleViewHoler(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.fragment_phone_number_code_title, parent, false)
        )
    }

    override fun getItemCount(): Int = showingData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhoneNumberCodeTitleViewHoler -> {
                holder.binding(showingData[position].name)
            }

            is PhoneNumberCodeViewHoler -> {
                val data = showingData[position]
                val checked = (currentPhoneNumberCode == null && data.dialCode == "+84") ||
                        (currentPhoneNumberCode == data.code)
                holder.binding(data, checked, onPhoneNumberCodeSelected)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val country = showingData[position]
        return if (country.dialCode == null) TYPE_HEADER else TYPE_CONTENT
    }

    private fun formatData(data: List<PhoneNumberCode>) {
        showingData.clear()
        data.forEach { phoneNumberCode ->
            val next = previousChar != phoneNumberCode.name[0]
            if (next) {
                previousChar = phoneNumberCode.name[0]
                showingData.add(PhoneNumberCode().apply {
                    name = "${phoneNumberCode.name[0]}"
                })
            }

            showingData.add(phoneNumberCode)
        }
    }

    class PhoneNumberCodeViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun binding(
            country: PhoneNumberCode,
            checked: Boolean? = false,
            onClick: Consumer<PhoneNumberCode>?
        ) {
            val textView = itemView.findViewById<TextView>(R.id.text_country_and_code)
            textView.text = "${country.name} (${country.dialCode})"
            val imageView = itemView.findViewById<ImageView>(R.id.image_check)
            imageView.visibility = if (checked == true) View.VISIBLE else View.INVISIBLE
            itemView.setOnClickListener {
                savePhoneNumberCodeSelected(country)
                onClick?.accept(country)
            }
        }

        private fun savePhoneNumberCodeSelected(phoneNumberCode: PhoneNumberCode) {
            val shared = itemView.context.getSharedPreferences(
                SharedPreferenceKeys.AUTHENTICATION.name,
                MODE_PRIVATE
            )
            shared?.edit {
                putString(PARAM_ARG_PHONE_NUMBER_CODE, phoneNumberCode.code)
                putString(PARAM_ARG_PHONE_NUMBER_COUNTRY, phoneNumberCode.name)
                putString(PARAM_ARG_PHONE_NUMBER_DIAL_CODE, phoneNumberCode.dialCode)
                commit()
            }
        }
    }

    class PhoneNumberCodeTitleViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun binding(text: String) {
            val textView = itemView.findViewById<TextView>(R.id.text_title)
            textView.text = text
        }
    }

    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
    class PhoneNumberCode : Comparable<PhoneNumberCode> {
        lateinit var name: String
        var dialCode: String? = null
        var code: String? = null
        override fun compareTo(other: PhoneNumberCode): Int {
            return this.name.compareTo(other.name)
        }
    }

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_CONTENT = 1
    }
}