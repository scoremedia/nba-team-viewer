package com.example.nba.Ui.TeamList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nba.Data.Repo
import com.example.nba.Data.Team
import com.google.gson.GsonBuilder

class teamViewModel() :ViewModel(){

        private var data = MutableLiveData<List<Team>>()
        private var teamList:List<Team> = listOf(Team("theScore",1,0))

    init {
        data = Repo.getData()

    }

    fun getdata():LiveData<List<Team>>{
        return data
    }
    fun sort(){
        data.value = data.value?.sortedBy { it.full_name }
    }

    fun sortWins(){
        data.value = data.value?.sortedByDescending { it.wins }
    }
    fun sortLosses(){
        data.value = data.value?.sortedByDescending { it.losses }
    }




}