package com.dariushm2.thescore.view.teams_list

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dariushm2.thescore.R
import com.dariushm2.thescore.db.NbaDatabase
import com.dariushm2.thescore.remote.NbaApi
import com.dariushm2.thescore.repository.TeamsRepo
import com.dariushm2.thescore.util.BaseViewModelFactory
import com.dariushm2.thescore.util.DataClassMapper
import com.dariushm2.thescore.util.DataState


class TeamsListFragment : Fragment() {

    private val vm: TeamsViewModel by lazy {
        ViewModelProvider(this, BaseViewModelFactory {
            TeamsViewModel(
                null,
                TeamsRepo(
                    NbaApi(requireContext()),
                    NbaDatabase.getDatabase(requireContext()).getNbaDao(),
                    DataClassMapper()
                )
            )
        }).get(TeamsViewModel::class.java)
    }

    private lateinit var adapter: TeamsListAdapter
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

        setTitle()

        with(view) {
            recyclerView = findViewById(R.id.recyclerView)
            swipeRefresh = findViewById(R.id.swipeRefresh)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    private fun loading() {
        swipeRefresh.isEnabled = true
        swipeRefresh.isRefreshing = true
    }

    private fun finishLoading() {
        swipeRefresh.isEnabled = false
        swipeRefresh.isRefreshing = false
    }

    private fun setTitle() {
        activity?.title = getString(R.string.app_name)
        (activity as AppCompatActivity?)!!.supportActionBar?.subtitle = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vm.teamsLiveData.observe(viewLifecycleOwner, { it ->

            when(it) {
                is DataState.Loading -> {
                    loading()
                }
                is DataState.Success -> {
                    it.data.observe(viewLifecycleOwner, {
                        finishLoading()
                        vm.sort()
                        adapter = TeamsListAdapter(this, it)
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.forEach { it.isVisible = true }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.actionSortByName -> vm.sortByName()
            R.id.actionSortByWins -> vm.sortByWins()
            R.id.actionSortByLosses -> vm.sortByLosses()

            else -> super.onOptionsItemSelected(item)
        }

        adapter.notifyDataSetChanged()
        return true
    }


}