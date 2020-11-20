package com.dariushm2.thescore.db.model


import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity
data class TeamEntity(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val teamId: Int,
    @SerializedName("wins")
    val wins: Int,
    @SerializedName("losses")
    val losses: Int,
    @SerializedName("full_name")
    val fullName: String,
)