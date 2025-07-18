package com.lamnguyen.zalo.ui.welcome.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.welcome.viewmodels.ViewPagerViewModel

class DotFragment : Fragment() {
    private val viewPagerViewModel: ViewPagerViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_dot, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dot = view.findViewById<View>(R.id.dot)
        viewPagerViewModel.dotPositionLiveData.observeForever { dotPosition ->
            dot.translationX =
                dotPosition * resources.getDimension(R.dimen._7dp) * 2
        }
    }
}