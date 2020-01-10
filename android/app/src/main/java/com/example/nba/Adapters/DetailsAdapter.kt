package com.example.nba.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nba.Data.Player
import com.example.nba.R
import kotlinx.android.synthetic.main.player_row.view.*

class DetailsAdapter(val playerList:List<Player>): RecyclerView.Adapter<ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = "Pos: "
        val num = "Num: "
        val player = playerList[position]
        holder.itemView.name.text = player.first_name + " " + playerList[position].last_name
        holder.itemView.position.text = pos + player.position
        holder.itemView.number.text = num + player.number.toString()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cell = inflater.inflate(R.layout.player_row,parent,false)
        return ViewHolder(cell)

    }

    override fun getItemCount(): Int {
        return playerList.count()
    }


}
