package com.danhtt.assignment.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.danhtt.assignment.R
import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.presentation.usecase.FilterUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject

class CurrencyAdapter : RecyclerView.Adapter<CurrencyViewHolder>(), Filterable, KoinComponent {
    private val filterUseCase: FilterUseCase by inject()

    private val currencyList = mutableListOf<Currency>()
    private val currencyListFiltered = mutableListOf<Currency>()
    private var onFavoriteClickListener: ((Currency) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_currency_item, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bindData(currencyListFiltered[position], onFavoriteClickListener)
    }

    override fun getItemCount() = currencyListFiltered.size

    fun updateData(currencies: List<Currency>, isNotifyDataSetChanged: Boolean = true) {
        currencyList.clear()
        currencyList.addAll(currencies)
        currencyListFiltered.clear()
        currencyListFiltered.addAll(currencies)
        if (isNotifyDataSetChanged) {
            notifyDataSetChanged()
        }
    }

    fun setOnFavoriteClickListener(listener: (Currency) -> Unit) {
        onFavoriteClickListener = listener
    }

    fun filteredSize() = currencyListFiltered.size

    override fun getFilter(): Filter {
        return object :Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                return FilterResults().also { it.values = filterUseCase.searchByName(query?.toString(), currencyList) }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                currencyListFiltered.clear()
                currencyListFiltered.addAll((filterResults?.values as? List<Currency>) ?: currencyList)
                notifyDataSetChanged()
            }
        }
    }
}