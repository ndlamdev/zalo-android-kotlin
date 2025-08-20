package com.lamnguyen.zalo.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.main.viewmodels.MainViewModel
import com.lamnguyen.zalo.ui.main.adapters.MessageFragmentStateAdapter

class MessageFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.totalMessageUnRead.value = 5
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout_message_pager)
        val pager = view.findViewById<ViewPager2>(R.id.pager_message)
        pager.adapter = MessageFragmentStateAdapter(this)
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            run {
                val title = when (position) {
                    0 -> "Ưu tiên"
                    1 -> "Khác"
                    else -> ""
                }

                tab.text = title
            }
        }.attach()
    }
}