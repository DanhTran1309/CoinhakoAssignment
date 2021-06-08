package com.danhtt.assignment.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.danhtt.assignment.R
import com.danhtt.assignment.common.presentation.clickWithDebounce
import com.danhtt.assignment.domain.model.StateEvent
import com.danhtt.assignment.presentation.viewmodel.CurrencyViewModel
import com.danhtt.assignment.presentation.SortByEnum
import com.danhtt.assignment.presentation.adapter.CurrencyAdapter
import kotlinx.android.synthetic.main.fragment_currency_list.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class CurrencyListFragment : Fragment() {
    private val viewModel: CurrencyViewModel by sharedViewModel()
    private lateinit var currencyAdapter: CurrencyAdapter
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
    ): View? {
        return inflater.inflate(R.layout.fragment_currency_list, container, false)
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
        shimmer_frame_layout?.stopShimmer()
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

    private fun observeDataChanged() {
        viewModel.currenciesStateEvent.observe(viewLifecycleOwner, {
            swipe_refresh_currencies?.let { swipeRefresh ->
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

        })

        viewModel.sortByLiveData.observe(viewLifecycleOwner, {
            it ?: return@observe
            updateSortByArrow(it)
            val pricesSorted = viewModel.sortPriceList(it)
            currencyAdapter.updateData(if (isFavorite) pricesSorted.filter { price -> price.isFavorite } else pricesSorted)
        })
    }

    private fun showLoadingView() {
        shimmer_frame_layout?.visibility = View.VISIBLE
        shimmer_frame_layout?.startShimmer()
        recycler_currencies?.visibility = View.GONE
    }

    private fun displayData() {
        shimmer_frame_layout?.visibility = View.GONE
        shimmer_frame_layout?.stopShimmer()
        recycler_currencies?.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        recycler_currencies?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = currencyAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }

    private fun setupSwipeRefreshLayout() {
        swipe_refresh_currencies?.let {
            if (isFavorite) {
                it.isEnabled = false
                return@let
            }
            it.setOnRefreshListener {
                viewModel.getAllCurrencies()
                viewModel.clearDisposable()
                viewModel.startIntervalUpdatePrices()
            }
        }
    }

    private fun checkEmptyData() {
        if (currencyAdapter.itemCount > 0) {
            constraint_labels?.visibility = View.VISIBLE
            recycler_currencies?.visibility = View.VISIBLE
            tv_empty_data?.visibility = View.GONE
            return
        }
        constraint_labels?.visibility = View.GONE
        recycler_currencies?.visibility = View.GONE
        tv_empty_data?.let {
            it.visibility = View.VISIBLE
            it.text = getString(if (isFavorite) R.string.message_empty_favorite else R.string.message_empty_data)
        }
    }

    private fun setupSortClickListener() {
        view_name_filter?.clickWithDebounce { sortByName() }
        view_price_filter?.clickWithDebounce { sortByPrice() }
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
        imv_name_up?.alpha = 0.5f
        imv_name_down?.alpha = 0.5f
        imv_price_up?.alpha = 0.5f
        imv_price_down?.alpha = 0.5f
    }

    private fun updateSortByArrow(sortByEnum: SortByEnum) {
        clearAllSortArrow()
        when (sortByEnum) {
            SortByEnum.NAME_ASCENDING -> imv_name_up?.alpha = 1.0f
            SortByEnum.NAME_DESCENDING -> imv_name_down?.alpha = 1.0f
            SortByEnum.PRICE_ASCENDING -> imv_price_up?.alpha = 1.0f
            SortByEnum.PRICE_DESCENDING -> imv_price_down?.alpha = 1.0f
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
