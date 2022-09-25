package com.solodilov.coinapp.data.datasource

import com.solodilov.coinapp.data.model.coin.CoinDto
import com.solodilov.coinapp.data.model.coindetail.CoinDetailDto
import com.solodilov.coinapp.data.network.CoinGeckoApi
import com.solodilov.coinapp.domain.entity.Currency
import javax.inject.Inject

class CoinDataSourceImpl @Inject constructor(
    private val api: CoinGeckoApi,
) : CoinDataSource {
    override suspend fun getCoinDtoList(currency: Currency): List<CoinDto> =
        api.getCoinList(currency.name)

    override suspend fun getCoinDetailDto(id: String): CoinDetailDto =
        api.getCoinDetail(id)

}