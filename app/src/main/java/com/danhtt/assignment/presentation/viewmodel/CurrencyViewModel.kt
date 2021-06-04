package com.danhtt.assignment.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.presentation.SortByEnum
import com.danhtt.assignment.presentation.usecase.AddFavoriteUseCase
import com.danhtt.assignment.presentation.usecase.DeleteFavoriteUseCase
import com.danhtt.assignment.presentation.usecase.GetAllCurrenciesUseCase
import com.danhtt.assignment.presentation.usecase.SortByUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class CurrencyViewModel(
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val sortByUseCase: SortByUseCase
) : ViewModel() {
    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData
    private val _currenciesLiveData = MutableLiveData<List<Currency>>()
    val currenciesLiveData: LiveData<List<Currency>> = _currenciesLiveData
    private val _sortByLiveData = MutableLiveData(SortByEnum.NONE)
    val sortByLiveData: LiveData<SortByEnum> = _sortByLiveData

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getAllCurrencies(isShowLoading: Boolean = false) {
        if (isShowLoading) {
            _loadingLiveData.value = true
        }
        viewModelScope.launch {
            try {
                val prices = getAllCurrenciesUseCase.fetch(COUNTER_CURRENCY)
                _currenciesLiveData.value = prices
            } catch (e: Exception) {
                e.printStackTrace()
                _currenciesLiveData.value = emptyList()
            } finally {
                if (isShowLoading) {
                    _loadingLiveData.value = false
                }
            }
        }
    }

    fun addFavorite(currency: Currency) {
        viewModelScope.launch {
            try {
                addFavoriteUseCase.addFavorite(currency)
                updateFavoriteItem(currency.name, true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteFavorite(name: String) {
        viewModelScope.launch {
            try {
                deleteFavoriteUseCase.deleteByName(name)
                updateFavoriteItem(name, false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun sortPriceList(sortBy: SortByEnum): List<Currency> {
        val currencies = _currenciesLiveData.value ?: emptyList()
        if (currencies.isEmpty()) {
            return emptyList()
        }
        return when (sortBy) {
            SortByEnum.NONE -> currencies
            SortByEnum.NAME_ASCENDING -> sortByUseCase.sortByNameAscending(currencies)
            SortByEnum.NAME_DESCENDING -> sortByUseCase.sortByNameDescending(currencies)
            SortByEnum.PRICE_ASCENDING -> sortByUseCase.sortByPriceAscending(currencies)
            SortByEnum.PRICE_DESCENDING -> sortByUseCase.sortByPriceDescending(currencies)
        }
    }

    fun updateSortBy(sortBy: SortByEnum) {
        _sortByLiveData.value = sortBy
    }

    fun getCurrentSortedBy() = _sortByLiveData.value ?: SortByEnum.NONE

    fun startIntervalUpdatePrices() {
        Observable.interval(INTERVAL_PERIOD, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { getAllCurrencies() },
                { e ->
                    e.printStackTrace()
                    startIntervalUpdatePrices()
                }
            )
            .addTo(compositeDisposable)
    }

    fun clearDisposable() {
        compositeDisposable.clear()
    }

    private fun updateFavoriteItem(name: String, isFavorite: Boolean) {
        val currencies = _currenciesLiveData.value ?: emptyList()
        currencies.map {
            if (it.name == name) {
                it.isFavorite = isFavorite
            }
            it
        }
        _currenciesLiveData.value = currencies
    }

    companion object {
        private const val INTERVAL_PERIOD = 30L
        private const val COUNTER_CURRENCY = "USD"
    }
}
