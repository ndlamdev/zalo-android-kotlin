package com.lamnguyen.zalo.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lamnguyen.zalo.ui.main.viewmodels.MessageFragmentViewModel
import com.lamnguyen.zalo.ui.message.fragments.ListRoomChatFragment

class MessageFragmentStateAdapter(
    fragment: Fragment,
    val messageFragmentViewModel: MessageFragmentViewModel,
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ListRoomChatFragment(messageFragmentViewModel)
            else -> Fragment()
        }
    }
}