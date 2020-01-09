package com.example.nba.Ui.TeamList

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nba.Data.Repo
import com.example.nba.Data.Team
import com.example.nba.MainAdapter
import com.example.nba.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val vM = ViewModelProviders.of(this).get(teamViewModel::class.java)

        val wins = "Wins"
        val losses = "Losses"
        points.setText(wins)
        recyclerView_main.layoutManager = LinearLayoutManager(this)
        recyclerView_main.adapter = MainAdapter(listOf())

        //setting recycler view
        vM.getdata().observe(this, Observer {

            recyclerView_main.adapter = MainAdapter(it)
        })
//sort buttons
        imageButton.setOnClickListener { vM.sort() }
        points.setOnClickListener{
            if (points.text == wins){
                vM.sortWins()
                points.text = losses
            }
            else{
                vM.sortLosses()
                points.text = wins
            }
        }








    }



}


