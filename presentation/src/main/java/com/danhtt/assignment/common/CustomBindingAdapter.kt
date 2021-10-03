package com.danhtt.assignment.common

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.danhtt.assignment.presentation.R

@BindingAdapter(value = ["setIconUrl"])
fun ImageView.loadIcon(iconUrl: String) {
    Glide.with(this)
        .load(iconUrl)
        .into(this)
}

@BindingAdapter(value = ["setIsFavorite"])
fun ImageView.setFavoriteIcon(isFavorite: Boolean) {
    val starBorderDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_star_border)
    val starDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_star)
    setImageDrawable(if (isFavorite) starDrawable else starBorderDrawable)
}
