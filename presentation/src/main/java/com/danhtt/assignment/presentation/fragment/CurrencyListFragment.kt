package com.danhtt.assignment.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.danhtt.assignment.common.clickWithDebounce
import com.danhtt.assignment.domain.model.StateEvent
import com.danhtt.assignment.presentation.R
import com.danhtt.assignment.presentation.viewmodel.CurrencyViewModel
import com.danhtt.assignment.presentation.SortByEnum
import com.danhtt.assignment.presentation.adapter.CurrencyAdapter
import com.danhtt.assignment.presentation.databinding.FragmentCurrencyListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CurrencyListFragment : Fragment() {
    private val viewModel: CurrencyViewModel by lazy {
        ViewModelProvider(requireActivity())[CurrencyViewModel::class.java]
    }

    private lateinit var binding: FragmentCurrencyListBinding
    @Inject lateinit var currencyAdapter: CurrencyAdapter
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFavorite = arguments?.getBoolean(IS_FAVORITE_PARAMS) ?: false
        initAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwipeRefreshLayout()
        setupSortClickListener()
        observeDataChanged()
    }

    override fun onPause() {
        super.onPause()
        binding.shimmerFrameLayout.stopShimmer()
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

    private fun observeDataChanged() {
        viewModel.currenciesStateEvent.observe(viewLifecycleOwner) {
            binding.swipeRefreshCurrencies.let { swipeRefresh ->
                if (swipeRefresh.isRefreshing) {
                    swipeRefresh.isRefreshing = false
                }
            }
            when (it) {
                is StateEvent.Loading -> showLoadingView()
                is StateEvent.Success -> {
                    displayData()
                    val data = it.data
                    if (data.isNullOrEmpty()) {
                        return@observe
                    }
                    val list = viewModel.sortPriceList(viewModel.getCurrentSortedBy())
                    currencyAdapter.updateData(if (isFavorite) list.filter { price -> price.isFavorite } else list)
                    checkEmptyData()
                }
                is StateEvent.Failure -> {
                    displayData()
                }
            }

        }

        viewModel.sortByLiveData.observe(viewLifecycleOwner) {
            it ?: return@observe
            updateSortByArrow(it)
            val pricesSorted = viewModel.sortPriceList(it)
            currencyAdapter.updateData(if (isFavorite) pricesSorted.filter { price -> price.isFavorite } else pricesSorted)
        }
    }

    private fun showLoadingView() {
        binding.shimmerFrameLayout.apply {
            visibility = View.VISIBLE
            startShimmer()
        }
        binding.recyclerCurrencies.visibility = View.GONE
    }

    private fun displayData() {
        binding.shimmerFrameLayout.apply {
            visibility = View.GONE
            stopShimmer()
        }
        binding.recyclerCurrencies.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        binding.recyclerCurrencies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = currencyAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshCurrencies.apply {
            if (isFavorite) {
                isEnabled = false
                return@apply
            }
            setOnRefreshListener {
                viewModel.getAllCurrencies()
                viewModel.cancelCoroutines()
                viewModel.startIntervalUpdatePrices()
            }
        }
    }

    private fun checkEmptyData() {
        if (currencyAdapter.itemCount > 0) {
            binding.constraintLabels.visibility = View.VISIBLE
            binding.recyclerCurrencies.visibility = View.VISIBLE
            binding.tvEmptyData.visibility = View.GONE
            return
        }
        binding.constraintLabels.visibility = View.GONE
        binding.recyclerCurrencies.visibility = View.GONE
        binding.tvEmptyData.apply {
            visibility = View.VISIBLE
            text =
                getString(if (isFavorite) R.string.message_empty_favorite else R.string.message_empty_data)
        }
    }

    private fun setupSortClickListener() {
        binding.viewNameFilter.clickWithDebounce { sortByName() }
        binding.viewPriceFilter.clickWithDebounce { sortByPrice() }
    }

    private fun sortByName() {
        val currentSortBy = viewModel.getCurrentSortedBy()
        if (currentSortBy != SortByEnum.NAME_ASCENDING && currentSortBy != SortByEnum.NAME_DESCENDING) {
            viewModel.updateSortBy(SortByEnum.NAME_ASCENDING)
            return
        }
        if (currentSortBy == SortByEnum.NAME_ASCENDING) {
            viewModel.updateSortBy(SortByEnum.NAME_DESCENDING)
            return
        }
        viewModel.updateSortBy(SortByEnum.NONE)
    }

    private fun sortByPrice() {
        val currentSortBy = viewModel.getCurrentSortedBy()
        if (currentSortBy != SortByEnum.PRICE_ASCENDING && currentSortBy != SortByEnum.PRICE_DESCENDING) {
            viewModel.updateSortBy(SortByEnum.PRICE_DESCENDING)
            return
        }
        if (currentSortBy == SortByEnum.PRICE_DESCENDING) {
            viewModel.updateSortBy(SortByEnum.PRICE_ASCENDING)
            return
        }
        viewModel.updateSortBy(SortByEnum.NONE)
    }

    private fun clearAllSortArrow() {
        binding.imvNameUp.alpha = 0.5f
        binding.imvNameDown.alpha = 0.5f
        binding.imvPriceUp.alpha = 0.5f
        binding.imvPriceDown.alpha = 0.5f
    }

    private fun updateSortByArrow(sortByEnum: SortByEnum) {
        clearAllSortArrow()
        when (sortByEnum) {
            SortByEnum.NAME_ASCENDING -> binding.imvNameUp.alpha = 1.0f
            SortByEnum.NAME_DESCENDING -> binding.imvNameDown.alpha = 1.0f
            SortByEnum.PRICE_ASCENDING -> binding.imvPriceUp.alpha = 1.0f
            SortByEnum.PRICE_DESCENDING -> binding.imvPriceDown.alpha = 1.0f
            SortByEnum.NONE -> Unit
        }
    }

    companion object {
        private const val IS_FAVORITE_PARAMS = "is_favorite"

        fun newInstance(isFavorite: Boolean = false) = CurrencyListFragment().also {
            it.arguments = Bundle().also { bundle ->
                bundle.putBoolean(IS_FAVORITE_PARAMS, isFavorite)
            }
        }
    }
}
