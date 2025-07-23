package com.lamnguyen.zalo.ui.phonenumbercode.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.phonenumbercode.viewmodels.PhoneNumberViewModel


class PhoneNumberCountrySearchFragment : Fragment() {
    private lateinit var layoutSearch: LinearLayout
    private lateinit var editCountry: EditText
    private lateinit var layoutCleanSearch: FrameLayout
    private val phoneNumberViewModel: PhoneNumberViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_phone_number_country_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutSearch = view.findViewById(R.id.sub_layout_search)
        editCountry = view.findViewById(R.id.edit_country)
        layoutCleanSearch = view.findViewById(R.id.layout_button_clean_search)
        layoutCleanSearch.setOnClickListener {
            editCountry.text.clear()
        }
        editCountry.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            onSearchFocus(hasFocus)
        }

        editCountry.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                phoneNumberViewModel.textSearchLiveData.value = text.toString()
                layoutCleanSearch.visibility =
                    if (text?.isNotEmpty() == true) View.VISIBLE else View.INVISIBLE
            })
    }

    private fun onSearchFocus(hasFocus: Boolean) {
        layoutSearch.isSelected = hasFocus
        layoutCleanSearch.visibility =
            if (hasFocus && editCountry.text.isNotEmpty()) View.VISIBLE else View.INVISIBLE
    }

}