package com.danhtt.assignment.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.danhtt.assignment.R
import com.danhtt.assignment.common.presentation.clickWithDebounce
import com.danhtt.assignment.presentation.adapter.MainFragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()

        view_bg_search_bar?.clickWithDebounce {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }

    private fun setupViewPager() {
        view_pager?.adapter = MainFragmentStateAdapter(requireActivity().supportFragmentManager, lifecycle)
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = if (position == 0) "Market" else "Favorite"
        }.attach()
    }
}
