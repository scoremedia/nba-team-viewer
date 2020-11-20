package com.dariushm2.thescore.util

import com.dariushm2.thescore.db.model.PlayerEntity
import com.dariushm2.thescore.db.model.TeamEntity
import com.dariushm2.thescore.remote.model.PlayerResponse
import com.dariushm2.thescore.remote.model.TeamResponse

class DataClassMapper() : EntityMapper {

    override fun mapToTeamEntity(teamResponse: TeamResponse): TeamEntity {
        return TeamEntity(
            teamResponse.teamId,
            teamResponse.wins,
            teamResponse.losses,
            teamResponse.fullName
        )
    }

    override fun mapToTeamEntities(teamsResponse: List<TeamResponse>): List<TeamEntity> {
        return teamsResponse.map { mapToTeamEntity(it) }
    }

    override fun mapToPlayerEntities(teamsResponse: List<TeamResponse>): List<PlayerEntity> {
        return teamsResponse.flatMap { teamResponse ->
            teamResponse.players.map { mapToPlayerEntity(teamResponse, it) }
        }
    }

//    override fun mapTeamsToPlayerEntities(teamsResponse: List<TeamResponse>): List<PlayerEntity> {
//        return teamsResponse.map { mapToPlayerEntities(it.) }
//    }

    override fun mapToPlayerEntity(teamResponse: TeamResponse, playerResponse: PlayerResponse): PlayerEntity {
        return PlayerEntity(
            playerResponse.playerId,
            teamResponse.teamId,
            playerResponse.firstName,
            playerResponse.lastName,
            playerResponse.position,
            playerResponse.number
        )
    }

}