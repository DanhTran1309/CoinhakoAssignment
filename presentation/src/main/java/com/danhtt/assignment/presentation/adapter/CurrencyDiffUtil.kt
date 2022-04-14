package com.danhtt.assignment.presentation.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.danhtt.assignment.domain.model.Currency

class CurrencyDiffUtil : DiffUtil.ItemCallback<Currency>() {

    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.name == newItem.name && oldItem.base == newItem.base
    }

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Currency, newItem: Currency): Any {
        return Bundle().also {
            it.putBoolean(EXTRA_IS_FAVORITE_CHANGED, oldItem.isFavorite != newItem.isFavorite)
            it.putBoolean(
                EXTRA_IS_PRICE_CHANGED,
                oldItem.buyPrice != newItem.buyPrice || oldItem.sellPrice != newItem.sellPrice
            )
        }
    }

    companion object {
        const val EXTRA_IS_FAVORITE_CHANGED = "EXTRA_IS_FAVORITE_CHANGED"
        const val EXTRA_IS_PRICE_CHANGED = "EXTRA_IS_PRICE_CHANGED"
    }
}
