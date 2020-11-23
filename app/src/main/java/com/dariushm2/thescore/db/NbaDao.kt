package com.dariushm2.thescore.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dariushm2.thescore.db.model.PlayerEntity
import com.dariushm2.thescore.db.model.TeamEntity
import com.dariushm2.thescore.db.model.TeamWithPlayers
import com.dariushm2.thescore.remote.model.TeamResponse
import com.dariushm2.thescore.util.DataState

@Dao
interface NbaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTeams(teamEntities: List<TeamEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTeam(teamEntity: TeamEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayers(teams: List<PlayerEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayer(playerEntity: PlayerEntity)


    @Query("SELECT * FROM TeamEntity")
    fun getAllTeams(): LiveData<MutableList<TeamEntity>>

    @Query("SELECT * FROM TeamEntity WHERE teamId = :teamId")
    fun getTeam(teamId: Int): LiveData<TeamEntity>

    @Transaction
    @Query("SELECT * FROM TeamEntity WHERE teamId = :teamId")
    fun getTeamWithPlayers(teamId: Int): LiveData<TeamWithPlayers>

    @Query("SELECT * FROM PlayerEntity WHERE playerTeamId = :teamId")
    fun getTeamPlayers(teamId: Int): LiveData<MutableList<PlayerEntity>>
}