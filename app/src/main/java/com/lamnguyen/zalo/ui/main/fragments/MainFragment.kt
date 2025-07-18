package com.lamnguyen.zalo.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.main.viewmodels.NavigationViewModel
import com.lamnguyen.zalo.utils.adapters.MainFragmentStateAdapter

class MainFragment : Fragment() {
    private val navigationViewModel: NavigationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pager = view.findViewById<ViewPager2>(R.id.pager_main)
        pager.adapter = MainFragmentStateAdapter(this)

        navigationViewModel.pageIndexLiveData.observe(viewLifecycleOwner) { page ->
            if (pager.currentItem == page) return@observe
            pager.setCurrentItem(page, false)
        }

        pager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (navigationViewModel.pageIndexLiveData.value == position) return
                navigationViewModel.pageIndexLiveData.value = position
            }
        })
    }
}