package com.solodilov.coinapp.presentation.coinlist

import com.solodilov.coinapp.domain.entity.Coin

sealed class CoinListScreenState {

    object Loading : CoinListScreenState()
    data class Content(val content: List<Coin>) : CoinListScreenState()
    object Error : CoinListScreenState()

}