package com.lamnguyen.zalo.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lamnguyen.zalo.ui.main.fragments.ContactFragment
import com.lamnguyen.zalo.ui.main.fragments.DiscoverFragment
import com.lamnguyen.zalo.ui.main.fragments.HistoryFragment
import com.lamnguyen.zalo.ui.main.fragments.MessageFragment
import com.lamnguyen.zalo.ui.main.fragments.PersonalFragment

class MainFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 5;
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MessageFragment()
            1 -> ContactFragment()
            2 -> DiscoverFragment()
            3 -> HistoryFragment()
            else -> PersonalFragment()
        }
    }
}