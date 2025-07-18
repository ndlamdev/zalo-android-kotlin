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
import com.lamnguyen.zalo.utils.adapters.IntroductionMainFragmentStateAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [IntroductionViewPagerMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IntroductionViewPagerMainFragment : Fragment() {
    private val viewPagerViewModel: ViewPagerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_introduction_view_pager_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = view.findViewById<ViewPager2>(R.id.pager_introduction)
        viewPager.adapter = IntroductionMainFragmentStateAdapter(this)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                viewPagerViewModel.isParentSwipeLiveData.value =
                    position != 0 || positionOffset != 0f
                if ((viewPagerViewModel.dotPositionLiveData.value as Float) < 3f) return
                viewPagerViewModel.dotPositionLiveData.value = 3 + position + positionOffset
            }
        })
        viewPagerViewModel.isParentSwipeLiveData.observe(viewLifecycleOwner) { isParentSwipe ->
            if (isParentSwipe == viewPager.isUserInputEnabled) return@observe
            viewPager.isUserInputEnabled = isParentSwipe
        }
    }
}