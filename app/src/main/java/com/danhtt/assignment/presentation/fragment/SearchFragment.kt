package com.danhtt.assignment.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.danhtt.assignment.R
import com.danhtt.assignment.common.presentation.clickWithDebounce
import com.danhtt.assignment.domain.model.StateEvent
import com.danhtt.assignment.presentation.viewmodel.CurrencyViewModel
import com.danhtt.assignment.presentation.adapter.CurrencyAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.recycler_currencies
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment() {
    private val viewModel: CurrencyViewModel by sharedViewModel()
    private lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imv_back?.clickWithDebounce { findNavController().navigateUp() }

        setupSearchEditText()
        setupRecyclerView()
        observeDataChanged()

        imv_clear_search?.clickWithDebounce { edt_search_input?.setText("") }
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
        currencyAdapter = CurrencyAdapter().also { adapter ->
            adapter.setOnFavoriteClickListener { price ->
                if (price.isFavorite) {
                    viewModel.deleteFavorite(price.name)
                } else {
                    viewModel.addFavorite(price)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        recycler_currencies?.apply {
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
                    val searchText = edt_search_input?.text?.trim()
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
        edt_search_input?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                imv_clear_search?.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                currencyAdapter.filter.filter(s) { checkEmptyData(true) }
            }

            override fun afterTextChanged(p0: Editable?) = Unit
        })
    }

    private fun checkEmptyData(isSearching: Boolean) {
        if (currencyAdapter.itemCount != 0) {
            recycler_currencies?.visibility = View.VISIBLE
            tv_empty_data?.visibility = View.GONE
            return
        }
        recycler_currencies?.visibility = View.GONE
        tv_empty_data?.let {
            it.visibility = View.VISIBLE
            it.text = getString(if (isSearching) R.string.message_search_empty_records else R.string.message_empty_data)
        }
    }

    private fun showSoftKeyboard() {
        edt_search_input?.let {
            it.requestFocus()
            val keyboard = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            keyboard?.showSoftInput(it, 0)
        }
    }

    private fun hideSoftKeyboard() {
        edt_search_input?.let {
            val keyboard = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            keyboard?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}
