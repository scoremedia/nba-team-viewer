package com.dariushm2.thescore.util

import com.dariushm2.thescore.db.model.PlayerEntity
import com.dariushm2.thescore.db.model.TeamEntity
import com.dariushm2.thescore.db.model.TeamWithPlayers
import com.dariushm2.thescore.remote.model.PlayerResponse
import com.dariushm2.thescore.remote.model.TeamResponse

interface EntityMapper {
    fun mapToPlayerEntity(teamResponse: TeamResponse, playerResponse: PlayerResponse): PlayerEntity
    fun mapToTeamEntity(teamResponse: TeamResponse): TeamEntity
    fun mapToTeamEntities(teamsResponse: List<TeamResponse>): List<TeamEntity>
    fun mapToPlayerEntities(teamsResponse: List<TeamResponse>): List<PlayerEntity>
    //fun mapTeamsToPlayerEntities(teamsResponse: List<TeamResponse>): List<PlayerEntity>
}