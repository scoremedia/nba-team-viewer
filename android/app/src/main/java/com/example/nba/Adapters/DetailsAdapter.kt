package com.example.nba.Adapters

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nba.Data.Player
import com.example.nba.R
import kotlinx.android.synthetic.main.player_row.view.*

class DetailsAdapter(val context: Context): RecyclerView.Adapter<DetailsViewHolder>() {
    var playerList:List<Player> = listOf()
    fun setItems(playerList:List<Player>){
        this.playerList = playerList
        notifyDataSetChanged()

    }
    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
       val pos = context.getString(R.string.pos)
        val num = context.getString(R.string.num)

        val player = playerList[position]
        with(holder.itemView){
            this.name.text = "${player.first_name} ${player.last_name}"
            this.position.text = "$pos ${player.position}"
            this.number.text = "$num ${player.number}"
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cell = inflater.inflate(R.layout.player_row,parent,false)
        return DetailsViewHolder(cell)

    }

    override fun getItemCount(): Int {
        return playerList.count()
    }


}

class DetailsViewHolder(v: View): RecyclerView.ViewHolder(v) {



}
