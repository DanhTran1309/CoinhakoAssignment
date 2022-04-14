package com.danhtt.assignment.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.danhtt.assignment.common.clickWithDebounce
import com.danhtt.assignment.common.setFavoriteIcon
import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.presentation.databinding.AdapterCurrencyItemBinding

class CurrencyViewHolder(val binding: AdapterCurrencyItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(currency: Currency, onFavoriteClickListener: (() -> Unit)? = null) {
        binding.currency = currency
        binding.imvFavorite.clickWithDebounce { onFavoriteClickListener?.invoke() }
        binding.executePendingBindings()
    }

    fun updateFavorite(isFavorite: Boolean) {
        binding.currency?.isFavorite = isFavorite
        binding.imvFavorite.setFavoriteIcon(isFavorite)
    }

    fun updatePrice(buyPrice: String, sellPrice: String) {
        binding.currency?.let {
            it.buyPrice = buyPrice
            it.sellPrice = sellPrice
        }
        binding.tvBuyPrice.text = buyPrice
        binding.tvSellPrice.text = sellPrice

    }
}
