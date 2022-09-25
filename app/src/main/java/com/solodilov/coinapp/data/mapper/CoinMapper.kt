package com.solodilov.coinapp.data.mapper

import com.solodilov.coinapp.data.model.coin.CoinDto
import com.solodilov.coinapp.data.model.coindetail.CoinDetailDto
import com.solodilov.coinapp.domain.entity.Coin
import com.solodilov.coinapp.domain.entity.CoinDetail
import com.solodilov.coinapp.domain.entity.Currency
import javax.inject.Inject

class CoinMapper @Inject constructor() {

    fun mapCoinDtoToCoin(coinDto: CoinDto, currency: Currency): Coin =
        Coin(
            id = coinDto.id,
            image = coinDto.image,
            name = coinDto.name,
            symbol = coinDto.symbol,
            currentPrice = coinDto.currentPrice,
            priceChangePercentage = coinDto.priceChangePercentage24h,
            currency = currency,
        )

    fun mapCoinDetailDtoToCoinDetail(coinDetailDto: CoinDetailDto): CoinDetail =
        CoinDetail(
            name = coinDetailDto.name,
            image = coinDetailDto.image.large,
            description = coinDetailDto.description.en,
            category = coinDetailDto.categories.joinToString()
        )

}