package com.dariushm2.thescore

import com.dariushm2.thescore.remote.NbaApi
import com.dariushm2.thescore.remote.model.PlayerResponse
import com.dariushm2.thescore.remote.model.TeamResponse
import retrofit2.Response
import kotlin.math.pow

class NbaApiFake() : NbaApi {
    override suspend fun getTeams(): Response<List<TeamResponse>> {
        return Response.success(
            listOf(
                TeamResponse(
                    1, 10, 12, "Toronto Raptors",
                    listOf(PlayerResponse(1, "LeBron", "James", "SG", 1))
                )
            )
        )
    }
}