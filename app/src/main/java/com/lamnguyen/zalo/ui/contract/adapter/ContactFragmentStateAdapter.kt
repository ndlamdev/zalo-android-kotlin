package com.lamnguyen.zalo.ui.contract.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lamnguyen.zalo.ui.contract.fragments.FriendFragment
import com.lamnguyen.zalo.ui.contract.fragments.GroupFragment
import com.lamnguyen.zalo.ui.contract.fragments.OAFragment

class ContactFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FriendFragment()
            1 -> GroupFragment()
            else -> OAFragment()
        }
    }

    override fun getItemCount(): Int = 3
}