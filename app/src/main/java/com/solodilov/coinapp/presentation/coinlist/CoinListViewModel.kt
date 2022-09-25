package com.solodilov.coinapp.presentation.coinlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodilov.coinapp.domain.entity.Currency
import com.solodilov.coinapp.domain.usecase.GetCoinListUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinListViewModel @Inject constructor(
    private val getCoinListUseCase: GetCoinListUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<CoinListScreenState>(CoinListScreenState.Loading)
    val state: LiveData<CoinListScreenState> = _state

    private val _currency = MutableLiveData<Currency>(Currency.USD)
    val currency: LiveData<Currency> = _currency

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    fun loadCoinList() {
        _currency.value?.let { currentCurrency ->
            _state.value = CoinListScreenState.Loading
            Log.d(TAG, "loadCoinList: ")
            viewModelScope.launch(exceptionHandler) {
                val coinList = getCoinListUseCase(currentCurrency)
                _state.postValue(CoinListScreenState.Content(content = coinList))
            }
        }
    }

    fun setCurrency(currency: String) {
        _currency.value = when (currency) {
            Currency.USD.name -> Currency.USD
            Currency.EUR.name -> Currency.EUR
            else -> null
        }
    }

    private fun handleError(error: Throwable) {
        Log.d(TAG, "handleError: ${error.message}")
        _state.value = CoinListScreenState.Error
    }

    companion object {
        const val TAG = "TAG"
    }
}