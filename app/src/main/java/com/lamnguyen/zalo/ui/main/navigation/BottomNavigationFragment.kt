package com.lamnguyen.zalo.ui.main.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.main.viewmodels.MainViewModel
import com.lamnguyen.zalo.ui.main.viewmodels.NavigationViewModel

class BottomNavigationFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNav = view.findViewById(R.id.bottom_nav)
        val badge = bottomNav.getOrCreateBadge(R.id.nav_message)
        badge.isVisible = true
        badge.number = 0
        mainViewModel.run {
            totalMessageUnRead.observe(viewLifecycleOwner) { total ->
                badge.number = total
            }
            totalHistoryUnSeen.observe(viewLifecycleOwner) { total ->
                println(total)
            }
        }

        bottomNav.setOnItemSelectedListener { item ->
            val newIndex = when (item.itemId) {
                R.id.nav_message -> 0
                R.id.nav_contact -> 1
                R.id.nav_discover -> 2
                R.id.nav_history -> 3
                R.id.nav_personal -> 4
                else -> return@setOnItemSelectedListener false
            }

            if (navigationViewModel.pageIndexLiveData.value != newIndex) {
                navigationViewModel.pageIndexLiveData.value = newIndex
            }
            true
        }

        navigationViewModel.pageIndexLiveData.observe(viewLifecycleOwner) { pageIndex ->
            val targetId = when (pageIndex) {
                0 -> R.id.nav_message
                1 -> R.id.nav_contact
                2 -> R.id.nav_discover
                3 -> R.id.nav_history
                4 -> R.id.nav_personal
                else -> return@observe
            }

            if (bottomNav.selectedItemId != targetId) {
                bottomNav.selectedItemId = targetId
            }
        }
    }
}