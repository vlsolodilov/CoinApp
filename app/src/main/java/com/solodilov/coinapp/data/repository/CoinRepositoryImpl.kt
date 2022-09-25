package com.solodilov.coinapp.data.repository

import com.solodilov.coinapp.data.datasource.CoinDataSource
import com.solodilov.coinapp.data.mapper.CoinMapper
import com.solodilov.coinapp.domain.entity.Coin
import com.solodilov.coinapp.domain.entity.CoinDetail
import com.solodilov.coinapp.domain.entity.Currency
import com.solodilov.coinapp.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val dataSource: CoinDataSource,
    private val mapper: CoinMapper,
) : CoinRepository {

    override suspend fun getCoinList(currency: Currency): List<Coin> =
        dataSource.getCoinDtoList(currency).map { coinDto ->
            mapper.mapCoinDtoToCoin(coinDto, currency)
        }

    override suspend fun getCoinDetail(id: String): CoinDetail =
        mapper.mapCoinDetailDtoToCoinDetail(dataSource.getCoinDetailDto(id))
}