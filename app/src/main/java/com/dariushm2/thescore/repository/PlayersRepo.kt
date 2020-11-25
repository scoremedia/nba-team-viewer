package com.dariushm2.thescore.repository

import com.dariushm2.thescore.db.NbaDao
import com.dariushm2.thescore.db.NbaDatabase

class PlayersRepo(
    private val teamId: Int,
    private val nbaDao: NbaDao
) {
    fun getTeamWithPlayers() = nbaDao.getTeamWithPlayers(teamId)
}