package com.solodilov.coinapp.data.model.coindetail


import com.google.gson.annotations.SerializedName

data class PublicInterestStats(
    @SerializedName("alexa_rank")
    val alexaRank: Int,
    @SerializedName("bing_matches")
    val bingMatches: Any?
)