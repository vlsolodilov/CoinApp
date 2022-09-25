package com.solodilov.coinapp.domain.usecase

import com.solodilov.coinapp.domain.entity.CoinDetail
import com.solodilov.coinapp.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(private val coinRepository: CoinRepository) {

    suspend operator fun invoke(id: String): CoinDetail =
        coinRepository.getCoinDetail(id)
}