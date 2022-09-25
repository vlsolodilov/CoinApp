package com.solodilov.coinapp.presentation.coindetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodilov.coinapp.domain.usecase.GetCoinDetailUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinDetailViewModel @Inject constructor(
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<CoinDetailScreenState>(CoinDetailScreenState.Loading)
    val state: LiveData<CoinDetailScreenState> = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    fun loadCoinDetail(coinId: String?) {
        coinId?.let { id ->
            _state.value = CoinDetailScreenState.Loading

            viewModelScope.launch(exceptionHandler) {
                val coinDetail = getCoinDetailUseCase(id)
                _state.postValue(CoinDetailScreenState.Content(content = coinDetail))
            }
        }
    }

    private fun handleError(error: Throwable) {
        Log.d(TAG, "handleError: ${error.message}")
        _state.value = CoinDetailScreenState.Error
    }

    companion object {
        const val TAG = "TAG"
    }
}