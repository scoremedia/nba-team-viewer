package com.dariushm2.thescore.view.teams_list

import androidx.lifecycle.*
import com.dariushm2.thescore.db.model.TeamEntity
import com.dariushm2.thescore.repository.TeamsRepo
import com.dariushm2.thescore.util.DataState
import com.dariushm2.thescore.util.getViewModelScope
import com.dariushm2.thescore.view.teams_list.TeamsViewModel.SortBy.*
import kotlinx.coroutines.CoroutineScope
import java.lang.Exception


class TeamsViewModel(
    coroutineScopeProvider: CoroutineScope? = null,
    private val repo: TeamsRepo
) : ViewModel() {

    private var _teamsLiveData: LiveData<MutableList<TeamEntity>> = MutableLiveData()


    enum class SortBy {
        NAME,
        WINS,
        LOSSES
    }

    var sortBy = NAME

    private val coroutineScope = getViewModelScope(coroutineScopeProvider)


    val teamsLiveData = liveData(coroutineScope.coroutineContext) {
        emit(DataState.Loading)
        try {
            _teamsLiveData = repo.getCachedTeams()
            emit(DataState.Success(_teamsLiveData))
            repo.getRemoteTeams()
        }
        catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }

    fun sort() {
        when (sortBy) {
            NAME -> sortByName()
            WINS -> sortByWins()
            LOSSES -> sortByLosses()
        }
    }

    fun sortByName() {
        sortBy = NAME

        _teamsLiveData.value?.sortBy { it.fullName }
    }

    fun sortByWins() {
        sortBy = WINS
        _teamsLiveData.value?.sortByDescending { it.wins }
    }

    fun sortByLosses() {
        sortBy = LOSSES
        _teamsLiveData.value?.sortByDescending { it.losses }
    }

}