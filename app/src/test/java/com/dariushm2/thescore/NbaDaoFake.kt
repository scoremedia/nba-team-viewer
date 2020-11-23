package com.dariushm2.thescore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dariushm2.thescore.db.NbaDao
import com.dariushm2.thescore.db.model.PlayerEntity
import com.dariushm2.thescore.db.model.TeamEntity
import com.dariushm2.thescore.db.model.TeamWithPlayers

class NbaDaoFake() : NbaDao {
    override suspend fun insertTeams(teamEntities: List<TeamEntity>) {
        return
    }

    override suspend fun insertTeam(teamEntity: TeamEntity) {
        return
    }

    override suspend fun insertPlayers(teams: List<PlayerEntity>) {
        return
    }

    override suspend fun insertPlayer(playerEntity: PlayerEntity) {
        return
    }

    override fun getAllTeams(): LiveData<MutableList<TeamEntity>> {
        return MutableLiveData(mutableListOf(TeamEntity(1, 10, 12, "Toronto Raptors")))
    }

    override fun getTeam(teamId: Int): LiveData<TeamEntity> {
        return MutableLiveData(TeamEntity(1, 10, 12, "Toronto Raptors"))
    }

    override fun getTeamWithPlayers(teamId: Int): LiveData<TeamWithPlayers> {
        return MutableLiveData(TeamWithPlayers(TeamEntity(1, 10, 12, "Toronto Raptors"),
            listOf(PlayerEntity(1, 1, "LeBron", "James", "SG", 1))))
    }

    override fun getTeamPlayers(teamId: Int): LiveData<MutableList<PlayerEntity>> {
        return MutableLiveData(mutableListOf(PlayerEntity(1, 1, "LeBron", "James", "SG", 1)))
    }

}