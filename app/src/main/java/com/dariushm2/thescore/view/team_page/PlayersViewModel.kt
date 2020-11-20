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

    private var _teamsLiveData: LiveData<MutableList<TeamEntity>> = MutableLiveData()

    private val coroutineScope = getViewModelScope(coroutineScopeProvider)

    val teamLiveData = liveData(coroutineScope.coroutineContext) {
        emit(DataState.Loading)
        try {
            emit(DataState.Success(repo.getTeam()))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    val playersLiveData = liveData(coroutineScope.coroutineContext) {
        emit(DataState.Loading)
        try {
            emit(DataState.Success(repo.getTeamsPlayers()))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}