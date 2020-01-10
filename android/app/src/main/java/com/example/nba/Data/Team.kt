package com.example.nba.Data

class Team(val full_name:String ,val wins: Int, val losses:Int ,val players: List<Player>) {
    constructor(full_name:String ,wins: Int,losses:Int) :this(full_name,wins,losses, listOf()){

    }
}