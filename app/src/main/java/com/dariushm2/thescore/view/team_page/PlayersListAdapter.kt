package com.dariushm2.thescore.view.team_page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dariushm2.thescore.R
import com.dariushm2.thescore.db.model.PlayerEntity
import com.dariushm2.thescore.remote.model.PlayerResponse


class PlayersListAdapter(
    private var players: List<PlayerEntity>
) : RecyclerView.Adapter<PlayersListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItem(players[position])

    }

    override fun getItemCount(): Int = players.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtFirstName: TextView = view.findViewById(R.id.txtFirstName)
        private val txtLastName: TextView = view.findViewById(R.id.txtLastName)
        private val txtNumber: TextView = view.findViewById(R.id.txtNumber)
        private val txtPosition: TextView = view.findViewById(R.id.txtPosition)

        fun bindItem(playerEntity: PlayerEntity) {
            txtFirstName.text = playerEntity.firstName
            txtLastName.text = playerEntity.lastName
            txtNumber.text = playerEntity.number.toString()
            txtPosition.text = playerEntity.position
        }

    }

}
