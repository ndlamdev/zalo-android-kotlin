package com.lamnguyen.zalo.ui.welcome.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lamnguyen.zalo.ui.welcome.fragments.ImageFragment
import com.lamnguyen.zalo.ui.welcome.fragments.IntroductionViewPagerFragment

class IntroductionMainFragmentStateAdapter(val fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IntroductionViewPagerFragment()
            else -> ImageFragment()
        }
    }
}