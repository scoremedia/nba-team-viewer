package com.dariushm2.thescore.view.teams_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dariushm2.thescore.R
import com.dariushm2.thescore.db.model.TeamEntity


class TeamsListAdapter(
    private val fragment: TeamsListFragment,
    private var teams: MutableList<TeamEntity>
) : RecyclerView.Adapter<TeamsListAdapter.ViewHolder>() {

    fun getTeamsList(): List<TeamEntity> = teams

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //holder.itemView.txtName.text = activity.getString(R.string.fullName, teams[position].fullName)

        holder.bindItem(teams[position])

        holder.itemView.setOnClickListener {
            val bundle = bundleOf("teamId" to teams[position].teamId)

            fragment.findNavController()
                .navigate(R.id.action_TeamsListFragment_to_PlayersListFragment, bundle)
        }

    }

    override fun getItemCount(): Int = teams.size

    fun addItems(teams: List<TeamEntity>) {
        this.teams.clear()
        this.teams.addAll(teams)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = view.findViewById(R.id.txtName)
        private val txtWins: TextView = view.findViewById(R.id.txtWins)
        private val txtLosses: TextView = view.findViewById(R.id.txtLosses)

        fun bindItem(teamEntity: TeamEntity) {
            txtName.text = teamEntity.fullName
            txtWins.text = teamEntity.wins.toString()
            txtLosses.text = teamEntity.losses.toString()
        }

    }

}
