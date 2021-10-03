package com.danhtt.assignment.presentation.activity

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.domain.model.StateEvent
import com.danhtt.assignment.presentation.R
import com.danhtt.assignment.presentation.databinding.ActivityMainBinding
import com.danhtt.assignment.presentation.receiver.NetworkReceiver
import com.danhtt.assignment.presentation.viewmodel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var networkReceiver: NetworkReceiver
    private lateinit var binding: ActivityMainBinding

    private val viewModel: CurrencyViewModel by lazy {
        ViewModelProvider(this).get(CurrencyViewModel::class.java)
    }
    private var backPressedTime = 0L

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        window?.decorView?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.systemUiVisibility = it.systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.let {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }
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

    override fun onBackPressed() {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.let {
            if (NavHostFragment.findNavController(it).currentDestination?.id == R.id.mainFragment) {
                if (backPressedTime + TIME_INTERVAL > System.currentTimeMillis()) {
                    super.onBackPressed()
                    return
                } else {
                    Toast.makeText(this, "Tap back button again to exit", Toast.LENGTH_SHORT).show()
                }
                backPressedTime = System.currentTimeMillis()
            } else {
                super.onBackPressed()
            }
        } ?: super.onBackPressed()

    }

    private fun initNetworkReceiver() {
        networkReceiver = NetworkReceiver { isConnected ->
            if (isConnected) {
                viewModel.setNoInternetConnectionVisibility(View.GONE)
                val currencies: List<Currency> = when (val event = viewModel.currenciesStateEvent.value) {
                    is StateEvent.Success -> event.data
                    else -> emptyList()
                }
                viewModel.getAllCurrencies(currencies.isNullOrEmpty())
                viewModel.clearDisposable()
                viewModel.startIntervalUpdatePrices()
            } else {
                viewModel.setNoInternetConnectionVisibility(View.VISIBLE)
                viewModel.clearDisposable()
            }
        }
    }

    companion object {
        private const val TIME_INTERVAL = 2000L
    }
}
