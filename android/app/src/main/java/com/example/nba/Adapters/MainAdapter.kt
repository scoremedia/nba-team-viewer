package com.example.nba.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nba.Data.Team
import com.example.nba.R
import kotlinx.android.synthetic.main.team_row.view.*

class MainAdapter(val context: Context,val listener: ClickListener): RecyclerView.Adapter<ViewHolder>() {
    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.unsetListener()

    }

    private var teams = listOf<Team>()
    fun setItems(teamList:List<Team>){
        this.teams = teamList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.team_row,parent,false)

        return ViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return teams.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val w = context.getString(R.string.winLabel)
        val l = context.getString(R.string.lossLabel)
        val team = teams[position]

        with(holder.itemView){
            this.teamName.text = "${team.full_name}"
            this.teamWins.text = "${w} ${team.wins.toString()}"
            this.teamLosses.text = "${l} ${team.losses.toString()}"
            this.setOnClickListener {
                listener.onTeamClicked(position)
            }

        }




    }



}

class ViewHolder(val v: View): RecyclerView.ViewHolder(v) {

    fun unsetListener(){
        v.setOnClickListener(null)
        Log.i("ViewHolder","called")
    }
}

interface ClickListener{
    fun onTeamClicked(pos:Int)
}