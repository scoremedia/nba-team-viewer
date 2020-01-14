package com.example.nba.Ui.TeamList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nba.Adapters.MainAdapter
import com.example.nba.Adapters.ClickListener
import com.example.nba.R
import kotlinx.android.synthetic.main.activity_main.*

const val KEY_ID = "id"


class MainActivity : AppCompatActivity(),ClickListener {

    override fun onTeamClicked(pos:Int) {
        val intent = Intent(this,DetailsActivity::class.java).apply {
            putExtra(KEY_ID,pos)
        }
        startActivity(intent)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(teamViewModel::class.java)
        if (savedInstanceState == null) {
              viewModel.loadData()
            }

        val wins = getString(R.string.wins)
        val losses = getString(R.string.losses)
        points.text = wins
        recyclerView_main.layoutManager = LinearLayoutManager(this)
        val mainAdapter = MainAdapter(this,this)
        recyclerView_main.adapter = mainAdapter


        //setting recycler view
        viewModel.data.observe(this, Observer {
            mainAdapter.setItems(it)
        })
//sort buttons
        imageButton.setOnClickListener { viewModel.sort() }
        points.setOnClickListener{
            if (points.text == wins){
                viewModel.sortWins()
                points.text = losses
            }
            else{
                viewModel.sortLosses()
                points.text = wins
            }
        }


    }





}


