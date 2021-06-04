package com.danhtt.assignment.presentation.activity

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import com.danhtt.assignment.R
import com.danhtt.assignment.presentation.receiver.NetworkReceiver
import com.danhtt.assignment.presentation.viewmodel.CurrencyViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: CurrencyViewModel by viewModel()
    private lateinit var networkReceiver: NetworkReceiver

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        window?.decorView?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.systemUiVisibility = it.systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNetworkReceiver()

        viewModel.getAllCurrencies(true)
        viewModel.startIntervalUpdatePrices()
    }

    @Suppress("DEPRECATION")
    override fun onStart() {
        super.onStart()
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkReceiver)
    }

    private fun initNetworkReceiver() {
        networkReceiver = NetworkReceiver { isConnected ->
            if (isConnected) {
                tv_no_internet_connection?.visibility = View.GONE
                viewModel.getAllCurrencies(viewModel.currenciesLiveData.value.isNullOrEmpty())
                viewModel.clearDisposable()
                viewModel.startIntervalUpdatePrices()
            } else {
                tv_no_internet_connection?.visibility = View.VISIBLE
                viewModel.clearDisposable()
            }
        }
    }
}
