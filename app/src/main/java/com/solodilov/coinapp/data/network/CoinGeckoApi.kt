package com.solodilov.coinapp.data.network

import com.solodilov.coinapp.data.model.coin.CoinDto
import com.solodilov.coinapp.data.model.coindetail.CoinDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {

    private companion object {
        const val ORDER = "market_cap_desc"
        const val COIN_PER_PAGE = "30"
    }

    @GET("v3/coins/markets?&order=$ORDER&per_page=$COIN_PER_PAGE&page=1&sparkline=false")
    suspend fun getCoinList(@Query("vs_currency") currency: String): List<CoinDto>

    @GET("v3/coins/{id}?localization=false&tickers=false&market_data=false&community_data=false" +
            "&developer_data=false&sparkline=false")
    suspend fun getCoinDetail(@Path("id") id: String): CoinDetailDto
}