package com.dariushm2.thescore.remote.model


import com.google.gson.annotations.SerializedName


data class TeamResponse(

    @SerializedName("id")
    val teamId: Int,
    @SerializedName("wins")
    val wins: Int,
    @SerializedName("losses")
    val losses: Int,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("players")
    val players: List<PlayerResponse>

)