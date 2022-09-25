package com.solodilov.coinapp.domain.usecase

import com.solodilov.coinapp.domain.entity.Coin
import com.solodilov.coinapp.domain.entity.Currency
import com.solodilov.coinapp.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinListUseCase @Inject constructor(private val coinRepository: CoinRepository) {

    suspend operator fun invoke(currency: Currency): List<Coin> =
        coinRepository.getCoinList(currency)
}