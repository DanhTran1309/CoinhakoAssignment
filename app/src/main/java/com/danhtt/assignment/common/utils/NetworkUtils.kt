package com.danhtt.assignment.common.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtils(context: Context) {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    @Suppress("DEPRECATION")
    fun isConnected() = connectivityManager?.activeNetworkInfo?.isConnected ?: false
}
