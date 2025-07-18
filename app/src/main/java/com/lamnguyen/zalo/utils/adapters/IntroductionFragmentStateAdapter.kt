package com.lamnguyen.zalo.utils.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.welcome.fragments.IntroductionFragment

class IntroductionFragmentStateAdapter(val fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IntroductionFragment.newInstance(
                R.drawable.ic_video,
                fragment.resources.getString(R.string.introduction_title_1),
                fragment.resources.getString(R.string.introduction_description_1),
            )

            1 -> IntroductionFragment.newInstance(
                R.drawable.ic_message,
                fragment.resources.getString(R.string.introduction_title_2),
                fragment.resources.getString(R.string.introduction_description_2),
            )

            2 -> IntroductionFragment.newInstance(
                R.drawable.ic_gallery,
                fragment.resources.getString(R.string.introduction_title_3),
                fragment.resources.getString(R.string.introduction_description_3),
            )

            else -> IntroductionFragment.newInstance(
                R.drawable.ic_book_contacts,
                fragment.resources.getString(R.string.introduction_title_4),
                fragment.resources.getString(R.string.introduction_description_4),
            )
        }
    }
}
