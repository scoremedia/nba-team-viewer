package com.dariushm2.thescore.repository

import com.dariushm2.thescore.db.NbaDao
import com.dariushm2.thescore.remote.NbaApi
import com.dariushm2.thescore.remote.model.TeamResponse
import com.dariushm2.thescore.util.DataClassMapper

class TeamsRepo(
    private val nbaApi: NbaApi,
    private val nbaDao: NbaDao,
    private val dataClassMapper: DataClassMapper
) {

    fun getCachedTeams() = nbaDao.getAllTeams()

    private suspend fun cacheTeams(teamsResponse: List<TeamResponse>) {

        val teamEntities = dataClassMapper.mapToTeamEntities(teamsResponse)
        nbaDao.insertTeams(teamEntities)

        val playerEntities = dataClassMapper.mapToPlayerEntities(teamsResponse)
        nbaDao.insertPlayers(playerEntities)
    }


    suspend fun getRemoteTeams() {
        val response = nbaApi.getTeams()
        if (response.isSuccessful) {
            //Log.e(NbaApp.TAG, "Fetched quote from server. ${response.body()}")
            if (response.body() != null)
                cacheTeams(response.body()!!)
        }
    }


}