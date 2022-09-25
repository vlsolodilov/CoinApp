package com.solodilov.coinapp.domain.entity

data class Coin(
    val id: String,
    val image: String,
    val name: String,
    val symbol: String,
    val currentPrice: Double,
    val priceChangePercentage: Double,
    val currency: Currency,
)
