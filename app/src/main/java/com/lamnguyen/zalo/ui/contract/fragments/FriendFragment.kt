package com.lamnguyen.zalo.ui.contract.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.configs.RetrofitClient
import com.lamnguyen.zalo.domain.dtos.User
import com.lamnguyen.zalo.ui.contract.adapter.FriendAdapter
import com.lamnguyen.zalo.utils.helpers.LogHelper
import kotlinx.coroutines.launch
import retrofit2.HttpException

class FriendFragment : Fragment() {
    lateinit var recyclerFriend: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerFriend = view.findViewById(R.id.recycler_friend)

        recyclerFriend.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
    }

    override fun onResume() {
        super.onResume()

        loadAllFriend()
    }

    private fun loadAllFriend() {
        lifecycleScope.launch {
            try {
                val data = RetrofitClient.userService(context).getAllFriend().data ?: listOf()
                recyclerFriend.adapter = FriendAdapter(data)
            } catch (e: HttpException) {
                LogHelper.showToastApiResponseError(context, e)
                LogHelper.errorWithClassName(this@FriendFragment.javaClass, e)
            } catch (e: Exception) {
                LogHelper.showToastError(context, e)
                LogHelper.errorWithClassName(this@FriendFragment.javaClass, e)
            }
        }
    }
}