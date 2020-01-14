package com.example.nba.Data

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException



class Repo {

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
                Log.e("Exception", e.toString())



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