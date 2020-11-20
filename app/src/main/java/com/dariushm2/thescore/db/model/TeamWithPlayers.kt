package com.dariushm2.thescore.db.model

import androidx.room.Embedded
import androidx.room.Relation

data class TeamWithPlayers(
    @Embedded val teamEntity: TeamEntity,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "playerTeamId"
    )
    val playerEntities: List<PlayerEntity>
)