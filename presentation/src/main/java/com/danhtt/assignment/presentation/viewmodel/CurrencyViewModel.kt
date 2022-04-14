package com.danhtt.assignment.presentation.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danhtt.assignment.domain.model.Currency
import com.danhtt.assignment.domain.model.StateEvent
import com.danhtt.assignment.domain.usecase.AddFavoriteUseCase
import com.danhtt.assignment.domain.usecase.DeleteFavoriteUseCase
import com.danhtt.assignment.domain.usecase.GetAllCurrenciesUseCase
import com.danhtt.assignment.domain.usecase.SortByUseCase
import com.danhtt.assignment.presentation.SortByEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val sortByUseCase: SortByUseCase
) : ViewModel() {
    private val _currenciesStateEvent = MutableLiveData<StateEvent<List<Currency>>>()
    val currenciesStateEvent: LiveData<StateEvent<List<Currency>>> = _currenciesStateEvent
    private val _sortByLiveData = MutableLiveData(SortByEnum.NONE)
    val sortByLiveData: LiveData<SortByEnum> = _sortByLiveData

    private val _noInternetConnectionVisibility = MutableLiveData(View.GONE)
    val noInternetConnectionVisibility: LiveData<Int> = _noInternetConnectionVisibility

    private var intervalJob: Job? = null

    fun setNoInternetConnectionVisibility(visibility: Int) {
        _noInternetConnectionVisibility.value = visibility
    }

    fun getAllCurrencies(isShowLoading: Boolean = false) {
        if (isShowLoading) {
            _currenciesStateEvent.value = StateEvent.Loading()
        }
        viewModelScope.launch {
            try {
                val prices = getAllCurrenciesUseCase.fetch(COUNTER_CURRENCY)
                _currenciesStateEvent.value = prices
            } catch (e: Exception) {
                e.printStackTrace()
                _currenciesStateEvent.value = StateEvent.Failure(e.message)
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
        val dataEvent = _currenciesStateEvent.value ?: return emptyList()
        if (dataEvent is StateEvent.Success) {
            val currencies = dataEvent.data
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
        return emptyList()
    }

    fun updateSortBy(sortBy: SortByEnum) {
        _sortByLiveData.value = sortBy
    }

    fun getCurrentSortedBy() = _sortByLiveData.value ?: SortByEnum.NONE

    fun startIntervalUpdatePrices() {
        intervalJob = viewModelScope.launch {
            delay(INTERVAL_PERIOD)
            try {
                val prices = getAllCurrenciesUseCase.fetch(COUNTER_CURRENCY)
                _currenciesStateEvent.value = prices
            } catch (e: Exception) {
                e.printStackTrace()
                _currenciesStateEvent.value = StateEvent.Failure(e.message)
            } finally {
                startIntervalUpdatePrices()
            }
        }
    }

    fun cancelCoroutines() {
        intervalJob?.cancel()
    }

    private fun updateFavoriteItem(name: String, isFavorite: Boolean) {
        val dataEvent = _currenciesStateEvent.value ?: return
        if (dataEvent is StateEvent.Success) {
            val currencies = dataEvent.data.toMutableList()
            currencies.map {
                if (it.name == name) {
                    it.isFavorite = isFavorite
                }
                it
            }
            _currenciesStateEvent.value = StateEvent.Success(currencies)
        }
    }

    companion object {
        private const val INTERVAL_PERIOD = 3000 * 1000L
        private const val COUNTER_CURRENCY = "USD"
    }
}
