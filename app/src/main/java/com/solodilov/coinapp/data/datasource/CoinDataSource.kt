package com.solodilov.coinapp.data.datasource

import com.solodilov.coinapp.data.model.coin.CoinDto
import com.solodilov.coinapp.data.model.coindetail.CoinDetailDto
import com.solodilov.coinapp.domain.entity.Currency

interface CoinDataSource {

    suspend fun getCoinDtoList(currency: Currency): List<CoinDto>
    suspend fun getCoinDetailDto(id: String): CoinDetailDto
}