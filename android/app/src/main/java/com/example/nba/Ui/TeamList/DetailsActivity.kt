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
    lateinit var team: Team
    val detailsAdapter = DetailsAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val id:Int? = intent.extras?.get(KEY_ID) as? Int

        val detailViewModel = ViewModelProviders.of(this).get(teamViewModel::class.java)
        detailViewModel.loadData()


        recyclerViewPlayers.layoutManager = LinearLayoutManager(this)
        recyclerViewPlayers.adapter = detailsAdapter

        detailViewModel.data.observe(this, Observer {
            onData(it,id)
        })



    }

    private fun onData(data:List<Team>?,id:Int?){
        if(id == null) return

        team = data?.get(id) ?: Team("theScore",1,0)
        teamWinsDetail.text = team.wins.toString()
        teamNameDeatil.text = team.full_name
        teamLossesDetail.text = team.losses.toString()
        detailsAdapter.setItems(team.players)
    }
}


