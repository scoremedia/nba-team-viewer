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
    private var list = mutableListOf<DataState<Any>>()
    private val teamWithPlayersObserver = mockk<Observer<DataState<Any>>>() {
        every {
            onChanged(capture(slot))
        } answers {
            list.add(slot.captured)
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

        viewModel.teamWithPlayersLiveData.observeForever(teamWithPlayersObserver)

        Truth.assertThat(list[0]).isEqualTo(DataState.Loading)
        Truth.assertThat(list[1] is DataState.Success || list[1] is DataState.Error).isTrue()

    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        // Reset Coroutine Dispatcher and Scope.
        testDispatcher.cleanupTestCoroutines()
        testScope.cleanupTestCoroutines()
    }


}