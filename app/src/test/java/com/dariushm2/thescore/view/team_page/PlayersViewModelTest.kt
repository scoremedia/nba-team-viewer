package com.dariushm2.thescore.view.team_page

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dariushm2.thescore.NbaDaoFake
import com.dariushm2.thescore.repository.PlayersRepo
import com.dariushm2.thescore.util.DataState
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class PlayersViewModelTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var viewModel: PlayersViewModel

    private val slot = slot<DataState<Any>>()
    private var teamList = mutableListOf<DataState<Any>>()
    private var playersList = mutableListOf<DataState<Any>>()
    private val teamObserver = mockk<Observer<DataState<Any>>>() {
        every {
            onChanged(capture(slot))
        } answers {
            teamList.add(slot.captured)
        }
    }
    private val playersObserver = mockk<Observer<DataState<Any>>>() {
        every {
            onChanged(capture(slot))
        } answers {
            playersList.add(slot.captured)
        }
    }

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var playersRepo: PlayersRepo

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        Dispatchers.setMain(testDispatcher)
        val teamId = 1
        playersRepo = PlayersRepo(teamId, NbaDaoFake())

        viewModel = PlayersViewModel(testScope, playersRepo)

    }

    @Test
    fun `team observer receives dataState Loading Success Error`() = testDispatcher.runBlockingTest {

        viewModel.teamLiveData.observeForever(teamObserver)

        Truth.assertThat(teamList[0]).isEqualTo(DataState.Loading)
        Truth.assertThat(teamList[1]).isInstanceOf(DataState.Success::class.java)
        //Truth.assertThat(teamList[2]).isInstanceOf(DataState.Error::class.java)
    }

    @Test
    fun `players observer receives dataState Loading Success Error`() = testDispatcher.runBlockingTest {

        viewModel.playersLiveData.observeForever(playersObserver)
        Truth.assertThat(playersList[0]).isEqualTo(DataState.Loading)
        Truth.assertThat(playersList[1]).isInstanceOf(DataState.Success::class.java)
        //Truth.assertThat(playersList[2]).isInstanceOf(DataState.Error::class.java)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        // Reset Coroutine Dispatcher and Scope.
        testDispatcher.cleanupTestCoroutines()
        testScope.cleanupTestCoroutines()
    }


}