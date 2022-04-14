package com.danhtt.assignment.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.domain.usecase.FilterUseCase
import com.danhtt.assignment.presentation.R
import com.danhtt.assignment.presentation.databinding.AdapterCurrencyItemBinding
import javax.inject.Inject

class CurrencyAdapter @Inject constructor(
    private val filterUseCase: FilterUseCase
) : ListAdapter<Currency, CurrencyViewHolder>(CurrencyDiffUtil()), Filterable {

    private val currencyList = mutableListOf<Currency>()
    private var onFavoriteClickListener: ((Currency) -> Unit)? = null
    private var onSubmitListCallback: ((Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding: AdapterCurrencyItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_currency_item,
            parent,
            false
        )
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bindData(currentList[position]) {
            onFavoriteClickListener?.invoke(currencyList[position])
        }
    }

    override fun onBindViewHolder(
        holder: CurrencyViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty() || payloads[0] !is Bundle) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        val currentCurrency = currentList[position]
        val bundle = payloads[0] as Bundle
        val isFavoriteChanged = bundle.getBoolean(CurrencyDiffUtil.EXTRA_IS_FAVORITE_CHANGED)
        val isPriceChanged = bundle.getBoolean(CurrencyDiffUtil.EXTRA_IS_PRICE_CHANGED)
        if (isFavoriteChanged) {
            holder.updateFavorite(currentCurrency.isFavorite)
        }
        if (isPriceChanged) {
            holder.updatePrice(currentCurrency.buyPrice, currentCurrency.sellPrice)
        }
    }

    override fun getItemCount() = currentList.size

    fun updateData(currencies: List<Currency>, isNotifyDataSetChanged: Boolean) {
        currencyList.clear()
        currencyList.addAll(currencies)
        if (isNotifyDataSetChanged) {
            val currenciesCopy = currencies.map { it.copy() }
            submitList(currenciesCopy) {
                onSubmitListCallback?.invoke(false)
            }
        }
    }

    fun setOnFavoriteClickListener(listener: (Currency) -> Unit) {
        onFavoriteClickListener = listener
    }

    fun setOnSubmitListCallback(listener: (Boolean) -> Unit) {
        onSubmitListCallback = listener
    }

    override fun getFilter(): Filter {
        return object :Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                return FilterResults().also {
                    it.values = filterUseCase.searchByName(query?.toString(), currencyList)
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                val currencies = (filterResults?.values as? List<Currency>) ?: currencyList
                val currenciesCopy = currencies.map { it.copy() }
                submitList(currenciesCopy) {
                    onSubmitListCallback?.invoke(true)
                }
            }
        }
    }
}
