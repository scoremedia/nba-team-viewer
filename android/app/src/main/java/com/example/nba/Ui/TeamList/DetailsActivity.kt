package com.example.nba.Ui.TeamList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nba.Adapters.DetailsAdapter
import com.example.nba.Data.Team
import com.example.nba.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        var team = Team("Score",1,0)
        val id:Int = intent.extras?.get("id") as Int
        val vM = ViewModelProviders.of(this).get(teamViewModel::class.java)
        rV.layoutManager = LinearLayoutManager(this)
        rV.adapter = DetailsAdapter(listOf())
        vM.getdata().observe(this, Observer {
            team = it[id]
            tW.text = team.wins.toString()
            tN.text = team.full_name
            tL.text = team.losses.toString()
            rV.adapter = DetailsAdapter(team.players)

        })


    }
}


