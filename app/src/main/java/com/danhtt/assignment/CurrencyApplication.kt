package com.danhtt.assignment

import android.app.Application
import com.danhtt.assignment.di.databaseModule
import com.danhtt.assignment.di.currencyModule
import com.danhtt.assignment.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class CurrencyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@CurrencyApplication)
            loadKoinModules(listOf(databaseModule, remoteModule, currencyModule))
        }
    }
}
