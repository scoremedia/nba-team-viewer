package com.example.nba.Ui.TeamList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nba.Data.Repo
import com.example.nba.Data.Team
import com.google.gson.GsonBuilder

class teamViewModel :ViewModel(){

        private var _data = MutableLiveData<List<Team>>()
        val data:LiveData<List<Team>>
            get() = _data
        private val repo = Repo()


    fun loadData() {
        _data = repo.getData()

    }

    fun sort(){
        _data.value = _data.value?.sortedBy { it.full_name }
    }

    fun sortWins(){
        _data.value = _data.value?.sortedByDescending { it.wins }
    }
    fun sortLosses(){
        _data.value = _data.value?.sortedByDescending { it.losses }
    }




}