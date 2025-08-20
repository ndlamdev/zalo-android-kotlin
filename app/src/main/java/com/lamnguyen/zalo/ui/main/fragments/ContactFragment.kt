package com.lamnguyen.zalo.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.contract.adapter.ContactFragmentStateAdapter
import androidx.core.view.size

class ContactFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.pager_contract)
        viewPager.adapter = ContactFragmentStateAdapter(this)
        tabLayout = view.findViewById(R.id.tab_layout_contact)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> resources.getString(R.string.friend)
                1 -> resources.getString(R.string.group)
                else -> resources.getString(R.string.o_a)
            }
        }.attach()

        reduceMarginsInTabs(tabLayout, resources.getDimension(R.dimen.small).toInt())
    }

    fun reduceMarginsInTabs(tabLayout: TabLayout, marginOffset: Int) {
        val tabStrip = tabLayout.getChildAt(0)
        if (tabStrip is ViewGroup) {
            for (i in 0..<tabStrip.size) {
                val tabView = tabStrip.getChildAt(i)
                val layoutParams = tabView.layoutParams
                if (layoutParams is MarginLayoutParams) {
                    layoutParams.leftMargin = marginOffset
                    layoutParams.rightMargin = marginOffset
                }
            }

            tabLayout.requestLayout()
        }
    }
}