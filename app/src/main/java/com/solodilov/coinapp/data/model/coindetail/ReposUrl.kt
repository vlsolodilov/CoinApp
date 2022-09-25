package com.solodilov.coinapp.data.model.coindetail


import com.google.gson.annotations.SerializedName

data class ReposUrl(
    @SerializedName("github")
    val github: List<String>,
    @SerializedName("bitbucket")
    val bitbucket: List<Any>
)