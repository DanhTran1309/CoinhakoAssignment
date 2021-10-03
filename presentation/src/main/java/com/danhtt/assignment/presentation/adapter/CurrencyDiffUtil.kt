package com.danhtt.assignment.presentation.adapter

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.danhtt.assignment.domain.model.Currency

class CurrencyDiffUtil(
    private val oldList: List<Currency>,
    private val newList: List<Currency>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.name == newItem.name && oldItem.base == newItem.base
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
//        return oldItem.sellPrice == newItem.sellPrice
//                && oldItem.buyPrice == newItem.buyPrice
//                && oldItem.isFavorite == newItem.isFavorite
    }
}
