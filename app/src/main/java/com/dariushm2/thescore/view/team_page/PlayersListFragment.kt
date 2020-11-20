package com.dariushm2.thescore.view.team_page

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dariushm2.thescore.R
import com.dariushm2.thescore.db.NbaDatabase
import com.dariushm2.thescore.db.model.TeamEntity
import com.dariushm2.thescore.repository.PlayersRepo
import com.dariushm2.thescore.util.BaseViewModelFactory
import com.dariushm2.thescore.util.DataState


class PlayersListFragment : Fragment() {

    private val vm: PlayersViewModel by lazy {

        val teamId = arguments?.getInt("teamId")

        ViewModelProvider(this, BaseViewModelFactory {
            PlayersViewModel(null, PlayersRepo(teamId!!, NbaDatabase.getDatabase(requireContext()).getNbaDao()))
        }).get(PlayersViewModel::class.java)

    }

    private lateinit var adapter: PlayersListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)

        with(view) {
            recyclerView = findViewById(R.id.recyclerView)
            swipeRefresh = findViewById(R.id.swipeRefresh)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    private fun loading() {
        swipeRefresh.isEnabled = false
        swipeRefresh.isRefreshing = true
    }

    private fun finishLoading() {
        swipeRefresh.isEnabled = false
        swipeRefresh.isRefreshing = false
    }



    private fun setTitle(team: TeamEntity) {
        activity?.title = team.fullName
        (activity as AppCompatActivity?)!!.supportActionBar?.subtitle = "${team.wins} Wins, ${team.losses} Losses"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vm.teamLiveData.observe(viewLifecycleOwner, { it ->

            when(it) {
                is DataState.Loading -> {
                    loading()
                }
                is DataState.Success -> {
                    finishLoading()
                    vm.playersLiveData.observe(viewLifecycleOwner, { it ->
                        when(it) {
                            is DataState.Loading -> {
                                loading()
                            }
                            is DataState.Success -> {
                                finishLoading()
                                it.data.observe(viewLifecycleOwner, { it ->
                                    it.sortBy { it.firstName }
                                    adapter = PlayersListAdapter(it)
                                    recyclerView.adapter = adapter
                                })

                            }
                            is DataState.Error -> {
                                finishLoading()
                                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_LONG).show()
                            }
                        }
                    })

                }
                is DataState.Error -> {
                    finishLoading()
                    Toast.makeText(context, it.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }


        })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.forEach { it.isVisible = false }
        super.onCreateOptionsMenu(menu, inflater)
    }

}