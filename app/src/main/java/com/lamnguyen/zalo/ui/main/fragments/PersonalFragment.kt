package com.lamnguyen.zalo.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.configs.RetrofitClient
import com.lamnguyen.zalo.ui.login.LoginActivity
import com.lamnguyen.zalo.utils.helpers.LogHelper
import com.lamnguyen.zalo.utils.helpers.TokenHelper
import kotlinx.coroutines.launch
import retrofit2.HttpException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PersonalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PersonalFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_logout).setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        lifecycleScope.launch {
            try {
                RetrofitClient.authService(this@PersonalFragment.context, false).logout()
                TokenHelper.cleanAccessToken(this@PersonalFragment.context)
                val intent = Intent(this@PersonalFragment.context, LoginActivity::class.java)
                    .apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }.putExtra(LoginActivity.ARG_HIDDEN_BUTTON_BACK, true)
                this@PersonalFragment.startActivity(intent)
            } catch (e: HttpException) {
                LogHelper.errorWithClassName(this@PersonalFragment, e)
                LogHelper.showToastApiResponseError(this@PersonalFragment.context, e)
            } catch (e: Exception) {
                LogHelper.errorWithClassName(this@PersonalFragment, e)
                LogHelper.showToastError(this@PersonalFragment.context, e)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PersonalFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PersonalFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}