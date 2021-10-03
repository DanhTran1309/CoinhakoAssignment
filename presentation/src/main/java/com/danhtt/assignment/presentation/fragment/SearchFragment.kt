package com.danhtt.assignment.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.danhtt.assignment.common.clickWithDebounce
import com.danhtt.assignment.domain.model.StateEvent
import com.danhtt.assignment.presentation.R
import com.danhtt.assignment.presentation.viewmodel.CurrencyViewModel
import com.danhtt.assignment.presentation.adapter.CurrencyAdapter
import com.danhtt.assignment.presentation.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: CurrencyViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CurrencyViewModel::class.java)
    }

    private lateinit var binding: FragmentSearchBinding
    @Inject lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imvBack.clickWithDebounce { findNavController().navigateUp() }

        setupSearchEditText()
        setupRecyclerView()
        observeDataChanged()

        binding.imvClearSearch.clickWithDebounce { binding.edtSearchInput.setText("") }
    }

    override fun onStart() {
        super.onStart()
        showSoftKeyboard()
    }

    override fun onStop() {
        super.onStop()
        hideSoftKeyboard()
    }

    private fun initAdapter() {
        currencyAdapter.apply {
            setOnFavoriteClickListener { price ->
                if (price.isFavorite) {
                    viewModel.deleteFavorite(price.name)
                } else {
                    viewModel.addFavorite(price)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerCurrencies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = currencyAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }

    private fun observeDataChanged() {
        viewModel.currenciesStateEvent.observe(viewLifecycleOwner, {
            when (it) {
                is StateEvent.Loading -> Unit
                is StateEvent.Success -> {
                    val data = it.data
                    if (data.isNullOrEmpty()) {
                        return@observe
                    }
                    val searchText = binding.edtSearchInput.text?.trim()
                    currencyAdapter.updateData(data, searchText.isNullOrEmpty())
                    checkEmptyData(false)
                    if (searchText.isNullOrEmpty()) {
                        return@observe
                    }
                    currencyAdapter.filter.filter(searchText) { checkEmptyData(true) }
                }
                is StateEvent.Failure -> Unit
            }
        })
    }

    private fun setupSearchEditText() {
        binding.edtSearchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.imvClearSearch.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                currencyAdapter.filter.filter(s) { checkEmptyData(true) }
            }

            override fun afterTextChanged(p0: Editable?) = Unit
        })
    }

    private fun checkEmptyData(isSearching: Boolean) {
        if (currencyAdapter.itemCount != 0) {
            binding.recyclerCurrencies.visibility = View.VISIBLE
            binding.tvEmptyData.visibility = View.GONE
            return
        }
        binding.recyclerCurrencies.visibility = View.GONE
        binding.tvEmptyData.apply {
            visibility = View.VISIBLE
            text = getString(if (isSearching) R.string.message_search_empty_records else R.string.message_empty_data)
        }
    }

    private fun showSoftKeyboard() {
        binding.edtSearchInput.let {
            it.requestFocus()
            val keyboard = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            keyboard?.showSoftInput(it, 0)
        }
    }

    private fun hideSoftKeyboard() {
        binding.edtSearchInput.let {
            val keyboard = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            keyboard?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}
