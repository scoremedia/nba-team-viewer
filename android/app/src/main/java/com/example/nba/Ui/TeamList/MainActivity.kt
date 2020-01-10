package com.example.nba.Ui.TeamList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nba.Adapters.MainAdapter
import com.example.nba.Adapters.clickListener
import com.example.nba.Data.Team
import com.example.nba.R
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),clickListener {
    override fun onTeamClicked(pos:Int) {
        val intent = Intent(this,DetailsActivity::class.java)
        intent.putExtra("id",pos)
        startActivity(intent)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val vM = ViewModelProviders.of(this).get(teamViewModel::class.java)

        val wins = "Wins"
        val losses = "Losses"
        points.setText(wins)
        recyclerView_main.layoutManager = LinearLayoutManager(this)
        recyclerView_main.adapter = MainAdapter(listOf(),this)

        //setting recycler view
        vM.getdata().observe(this, Observer {
            recyclerView_main.adapter = MainAdapter(it,this)
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


