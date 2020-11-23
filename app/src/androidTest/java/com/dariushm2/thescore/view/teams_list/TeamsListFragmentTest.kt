package com.dariushm2.thescore.view.teams_list

import android.view.View
import androidx.annotation.NonNull
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.dariushm2.thescore.R
import com.dariushm2.thescore.db.model.TeamEntity
import com.dariushm2.thescore.util.DataState
import com.dariushm2.thescore.view.MainActivity
import com.google.common.truth.Truth.assertThat
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TeamsListFragmentTest {

    companion object {
        fun atPosition(position: Int, @NonNull itemMatcher: Matcher<View?>): Matcher<View?>? {
            return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description) {
                    description.appendText("has item at position $position: ")
                    itemMatcher.describeTo(description)
                }

                override fun matchesSafely(view: RecyclerView): Boolean {
                    val viewHolder = view.findViewHolderForAdapterPosition(position)
                        ?: // has no item on such position
                        return false
                    return itemMatcher.matches(viewHolder.itemView)
                }
            }
        }
    }

    @Test
    fun testNavigationToTeamPage() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        navController.setGraph(R.navigation.nav_graph)


        val scenario = launchFragmentInContainer<TeamsListFragment>()

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.teamsListFragment)
        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<TeamsListAdapter.ViewHolder>(0, click()))
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.playersListFragment)
    }

    @Test
    fun testDataBinding() {
        val scenario = launchFragmentInContainer<TeamsListFragment>()

        var teamsList = mutableListOf<TeamEntity>()

        scenario.onFragment { fragment ->
            fragment.vm.teamsLiveData.observe(fragment.viewLifecycleOwner, { it ->
                if (it is DataState.Success) {
                    it.data.observe(fragment.viewLifecycleOwner, {
                        teamsList = it
                    })
                }
            })
        }

        if (teamsList.size > 0) {
            onView(withId(R.id.recyclerView))
                .check(matches(atPosition(0, hasDescendant(withText(teamsList[0].fullName)))))
            onView(withId(R.id.recyclerView))
                .check(matches(atPosition(0, hasDescendant(withText(teamsList[0].wins.toString())))))
            onView(withId(R.id.recyclerView))
                .check(matches(atPosition(0, hasDescendant(withText(teamsList[0].losses.toString())))))
        }
    }


    @Test
    fun testSortingActions() {

        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)

        var teamsList = mutableListOf<TeamEntity>()
        scenario.onActivity { activity ->
            val fragment = activity.supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment)
                ?.childFragmentManager!!.fragments[0] as TeamsListFragment
            fragment.vm.teamsLiveData.observe(activity , {
                if (it is DataState.Success) {
                    it.data.observe(fragment.viewLifecycleOwner, {
                        teamsList = it
                    })
                }
            })
        }

        if (teamsList.size > 0) {

            openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
            onView(withText(R.string.actionSortByWins)).perform(click())
            onView(withId(R.id.recyclerView))
                .check(matches(atPosition(0, hasDescendant(withText(teamsList[0].wins.toString())))))

            openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
            onView(withText(R.string.actionSortByLosses)).perform(click())
            onView(withId(R.id.recyclerView))
                .check(matches(atPosition(0, hasDescendant(withText(teamsList[0].losses.toString())))))

            openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
            onView(withText(R.string.actionSortByName)).perform(click())
            onView(withId(R.id.recyclerView))
                .check(matches(atPosition(0, hasDescendant(withText(teamsList[0].fullName)))))

        }

    }

}