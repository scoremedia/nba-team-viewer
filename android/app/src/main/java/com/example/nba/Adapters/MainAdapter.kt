package com.example.nba.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nba.Data.Team
import com.example.nba.R
import com.example.nba.Ui.TeamList.DetailsActivity
import kotlinx.android.synthetic.main.player_row.view.*
import kotlinx.android.synthetic.main.team_row.view.*

class MainAdapter(val teamList:List<Team>,val listener: clickListener): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.team_row,parent,false)

        return ViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return teamList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val w = "W: "
        val l = "L: "
        val team = teamList[position]
        holder.itemView.teamName.text =  team.full_name
        holder.itemView.teamWins.text = w + team.wins.toString()
        holder.itemView.teamLosses.text = l + team.losses.toString()
        holder.itemView.setOnClickListener {
            listener.onTeamClicked(position)
        }




    }
}

class ViewHolder(v: View): RecyclerView.ViewHolder(v) {



}

interface clickListener{
    fun onTeamClicked(pos:Int)
}