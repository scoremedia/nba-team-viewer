package com.example.nba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.team_row.view.*

class MainAdapter(val teamList:List<Team>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.team_row,parent,false)
        return ViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return teamList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team = teamList[position]
        holder.itemView.teamName.text = team.full_name
        holder.itemView.teamWins.text = team.wins.toString()
        holder.itemView.teamLosses.text = team.losses.toString()


    }
}

class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
    init {
        v.setOnClickListener {
            println("TEST")
        }
    }


}