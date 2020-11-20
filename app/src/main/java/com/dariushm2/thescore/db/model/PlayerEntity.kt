package com.dariushm2.thescore.db.model


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class PlayerEntity(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val playerId: Int,
    val playerTeamId: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("number")
    val number: Int
)