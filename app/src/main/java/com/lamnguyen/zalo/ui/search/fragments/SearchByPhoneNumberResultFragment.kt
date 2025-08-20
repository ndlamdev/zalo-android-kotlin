package com.lamnguyen.zalo.ui.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.search.adapters.UserAdapter
import com.lamnguyen.zalo.ui.search.viewmodels.SearchViewModel

class SearchByPhoneNumberResultFragment : Fragment() {
    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_by_phone_number_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rclUsers = view.findViewById<RecyclerView>(R.id.recycler_search_user_result)
        val txtAmountUser = view.findViewById<TextView>(R.id.text_amount_result)

        view.visibility = View.INVISIBLE
        searchViewModel.searchUserByPhoneNumberResultLiveData.observe(viewLifecycleOwner) { user ->
            view.visibility = if (user == null) View.INVISIBLE else View.VISIBLE

            if (user != null) {
                rclUsers.adapter = UserAdapter(listOf(user), lifecycleScope)
                rclUsers.layoutManager =
                    LinearLayoutManager(this@SearchByPhoneNumberResultFragment.context)
                txtAmountUser.text = " ( 1 )"
            }
        }
    }
}