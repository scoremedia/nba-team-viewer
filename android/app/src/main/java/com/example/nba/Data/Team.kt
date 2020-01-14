package com.example.nba.Data

data class Team(val full_name:String ,val wins: Int, val losses:Int ,val players: List<Player> = listOf()) {

}