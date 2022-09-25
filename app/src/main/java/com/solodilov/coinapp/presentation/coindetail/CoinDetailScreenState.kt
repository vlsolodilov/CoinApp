package com.solodilov.coinapp.presentation.coindetail

import com.solodilov.coinapp.domain.entity.CoinDetail

sealed class CoinDetailScreenState {

    object Loading : CoinDetailScreenState()
    data class Content(val content: CoinDetail) : CoinDetailScreenState()
    object Error : CoinDetailScreenState()

}