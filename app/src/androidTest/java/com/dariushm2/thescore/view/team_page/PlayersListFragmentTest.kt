package com.dariushm2.thescore.view.team_page

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dariushm2.thescore.R
import com.dariushm2.thescore.db.model.PlayerEntity
import com.dariushm2.thescore.util.DataState
import com.dariushm2.thescore.view.teams_list.TeamsListFragmentTest.Companion.atPosition
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlayersListFragmentTest {


    @Test
    fun testDataBinding() {
        val bundle = Bundle()
        bundle.putInt("teamId", 1)
        val scenario = launchFragmentInContainer<PlayersListFragment>(bundle)

        var players = listOf<PlayerEntity>()

        scenario.onFragment { fragment ->
            fragment.vm.teamWithPlayersLiveData.observe(fragment.viewLifecycleOwner, { it ->
                if (it is DataState.Success) {
                    it.data.observe(fragment.viewLifecycleOwner, {
                        players = it.playerEntities
                    })
                }
            })
        }

        if (players.isNotEmpty()) {
            onView(ViewMatchers.withId(R.id.recyclerView))
                .check(
                    ViewAssertions.matches(atPosition(0, ViewMatchers.hasDescendant(ViewMatchers.withText(players[0].firstName)))))
            onView(ViewMatchers.withId(R.id.recyclerView))
                .check(
                    ViewAssertions.matches(atPosition(0, ViewMatchers.hasDescendant(ViewMatchers.withText(players[0].lastName)))))
        }
    }

}