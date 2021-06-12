package com.danhtt.assignment.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.danhtt.assignment.common.presentation.clickWithDebounce
import com.danhtt.assignment.databinding.AdapterCurrencyItemBinding
import com.danhtt.assignment.domain.model.Currency

class CurrencyViewHolder(private val binding: AdapterCurrencyItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(currency: Currency, onFavoriteClickListener: ((Currency) -> Unit)? = null) {
        binding.currency = currency
        binding.imvFavorite.clickWithDebounce { onFavoriteClickListener?.invoke(currency) }
        binding.executePendingBindings()
    }
}
