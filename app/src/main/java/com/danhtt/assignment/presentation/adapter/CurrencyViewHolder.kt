package com.danhtt.assignment.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danhtt.assignment.R
import com.danhtt.assignment.common.presentation.clickWithDebounce
import com.danhtt.assignment.domain.model.Currency
import kotlinx.android.synthetic.main.adapter_currency_item.view.*

class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val context = view.context
    private val starBorderDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_star_border)
    private val starDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_star)

    private val imvCryptoIcon: ImageView? = view.imv_currency_icon
    private val tvCryptoBase: TextView? = view.tv_currency_base
    private val tvCryptoName: TextView? = view.tv_currency_name
    private val tvSellPrice: TextView? = view.tv_sell_price
    private val tvBuyPrice: TextView? = view.tv_buy_price
    private val imvFavorite: ImageView? = view.imv_favorite

    fun bindData(price: Currency, onFavoriteClickListener: ((Currency) -> Unit)? = null) {
        loadIcon(price.icon)
        tvCryptoBase?.text = price.base
        tvCryptoName?.text = price.name
        tvSellPrice?.text = context.getString(R.string.text_price_with_currency, price.sellPrice)
        tvBuyPrice?.text = context.getString(R.string.text_price_with_currency, price.buyPrice)
        handleFavorite(price, onFavoriteClickListener)
    }

    private fun loadIcon(iconUrl: String) {
        imvCryptoIcon?.let {
            Glide.with(context)
                .load(iconUrl)
                .into(it)
        }
    }

    private fun handleFavorite(
        currency: Currency,
        onFavoriteClickListener: ((Currency) -> Unit)? = null
    ) {
        imvFavorite?.let {
            it.setImageDrawable(if (currency.isFavorite) starDrawable else starBorderDrawable)
            it.clickWithDebounce { onFavoriteClickListener?.invoke(currency) }
        }
    }
}
