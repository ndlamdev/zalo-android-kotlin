package com.lamnguyen.zalo.ui.search.headers

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.configs.RetrofitClient
import com.lamnguyen.zalo.ui.main.MainActivity
import com.lamnguyen.zalo.utils.helpers.Debouncer

class SearchHeaderFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_header, container, false)
    }

    val debouncer = Debouncer(300)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userService = RetrofitClient.userService(context)

        view.findViewById<ImageView>(R.id.image_button_back).apply {
            setOnClickListener {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
        }

        view.findViewById<EditText>(R.id.edit_search).apply {
            addTextChangedListener(
                onTextChanged = { text, _, _, _ ->
                    debouncer.submitSuspend {
                        var phoneNumber = text.toString()
                        if (!phoneNumber.startsWith("+"))
                            phoneNumber = "+84${phoneNumber}"
                        val response = userService.searchUser(phoneNumber)
                        if (response.isSuccessful) {
                            val body = response.body()
                            println(body)
                        }

                        if (response.code() >= 400) {
                            val errorBody = response.errorBody()
                        }
                    }
                }
            )
        }
    }
}
