package com.example.nba.Data

import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


//Singleton

object Repo {

    private var teamList:List<Team> = listOf(Team("theScore",1,0))
    private var data = MutableLiveData<List<Team>>()

    fun getData(): MutableLiveData<List<Team>> {
        println("called")
        fetch()
        return data
    }



    private fun fetch(){

        val url = "https://api.myjson.com/bins/xmwjs"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e)



            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                teamList = gson.fromJson(body,Array<Team>::class.java).toList()
                data.postValue(teamList)




            }
        })

    }
}