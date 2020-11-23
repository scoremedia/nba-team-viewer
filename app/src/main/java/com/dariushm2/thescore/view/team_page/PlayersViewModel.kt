package com.dariushm2.thescore.view.team_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dariushm2.thescore.db.model.TeamEntity
import com.dariushm2.thescore.repository.PlayersRepo
import com.dariushm2.thescore.util.DataState
import com.dariushm2.thescore.util.getViewModelScope
import kotlinx.coroutines.CoroutineScope
import java.lang.Exception

class PlayersViewModel(
    coroutineScopeProvider: CoroutineScope? = null,
    private val repo: PlayersRepo
) : ViewModel() {

    private val coroutineScope = getViewModelScope(coroutineScopeProvider)

    val teamWithPlayersLiveData = liveData(coroutineScope.coroutineContext) {
        emit(DataState.Loading)
        try {
            emit(DataState.Success(repo.getTeamWithPlayers()))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}