package com.danhtt.assignment.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.danhtt.assignment.common.utils.NetworkUtils

class NetworkReceiver(
    private val onConnectedListener: ((Boolean) -> Unit)? = null
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.extras ?: return
        context ?: return
        onConnectedListener?.invoke(NetworkUtils(context).isConnected())
    }
}
