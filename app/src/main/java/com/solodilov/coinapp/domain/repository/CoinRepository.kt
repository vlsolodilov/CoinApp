package com.solodilov.coinapp.domain.repository

import com.solodilov.coinapp.domain.entity.Coin
import com.solodilov.coinapp.domain.entity.CoinDetail
import com.solodilov.coinapp.domain.entity.Currency

interface CoinRepository {

    suspend fun getCoinList(currency: Currency): List<Coin>
    suspend fun getCoinDetail(id: String): CoinDetail
}