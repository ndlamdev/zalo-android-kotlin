package com.lamnguyen.zalo.ui.welcome.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.welcome.viewmodels.ViewPagerViewModel
import com.lamnguyen.zalo.ui.welcome.adapters.IntroductionFragmentStateAdapter

class IntroductionViewPagerFragment : Fragment() {
    private val viewPagerViewModel: ViewPagerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_introduction_view_pager, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = view.findViewById<ViewPager2>(R.id.pager_introduction)
        viewPager.adapter = IntroductionFragmentStateAdapter(this)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val lastPage = (viewPager.adapter?.itemCount ?: 0) - 1
                viewPagerViewModel.isParentSwipeLiveData.value =
                    position == lastPage && positionOffset == 0f
                viewPagerViewModel.dotPositionLiveData.value = position + positionOffset
            }
        })

        viewPager.setPageTransformer { page, position ->
            view.translationX = page.translationX * position
        }
    }
}