package com.danhtt.assignment.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.danhtt.assignment.R
import com.danhtt.assignment.common.presentation.clickWithDebounce
import com.danhtt.assignment.databinding.FragmentMainBinding
import com.danhtt.assignment.presentation.adapter.MainFragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()

        binding.viewBgSearchBar.clickWithDebounce {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = MainFragmentStateAdapter(requireActivity().supportFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "Market" else "Favorite"
        }.attach()
    }
}
