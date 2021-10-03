package com.danhtt.assignment.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.danhtt.assignment.common.clickWithDebounce
import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.presentation.databinding.AdapterCurrencyItemBinding

class CurrencyViewHolder(private val binding: AdapterCurrencyItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(currency: Currency, onFavoriteClickListener: ((Currency) -> Unit)? = null) {
        binding.currency = currency
        binding.imvFavorite.clickWithDebounce { onFavoriteClickListener?.invoke(currency) }
        binding.executePendingBindings()
    }
}
