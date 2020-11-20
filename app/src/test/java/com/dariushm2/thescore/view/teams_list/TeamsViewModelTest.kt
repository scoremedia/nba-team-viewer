package com.dariushm2.thescore.view.teams_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dariushm2.thescore.NbaApiFake
import com.dariushm2.thescore.NbaDaoFake
import com.dariushm2.thescore.repository.TeamsRepo
import com.dariushm2.thescore.util.DataClassMapper
import com.dariushm2.thescore.util.DataState
import com.google.common.truth.Truth.assertThat
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
class TeamsViewModelTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var viewModel: TeamsViewModel

    private val slot = slot<DataState<Any>>()
    private var list = mutableListOf<DataState<Any>>()
    private val observer = mockk<Observer<DataState<Any>>>() {
        every {
            onChanged(capture(slot))
        } answers {
            list.add(slot.captured)
        }
    }

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var teamsRepo: TeamsRepo


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        Dispatchers.setMain(testDispatcher)
        teamsRepo = TeamsRepo(NbaApiFake(), NbaDaoFake(), DataClassMapper())

        viewModel = TeamsViewModel(testScope, teamsRepo)

    }

    @Test
    fun `observer receives dataState Loading Success Error`() = testDispatcher.runBlockingTest {

        viewModel.teamsLiveData.observeForever(observer)

        assertThat(list[0]).isEqualTo(DataState.Loading)
        assertThat(list[1]).isInstanceOf(DataState.Success::class.java)
        //assertThat(list[2]).isInstanceOf(DataState.Error::class.java)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        // Reset Coroutine Dispatcher and Scope.
        testDispatcher.cleanupTestCoroutines()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `sortByName saves state`() {
        viewModel.sortByName()
        assertThat(viewModel.sortBy).isEqualTo(TeamsViewModel.SortBy.NAME)
    }

    @Test
    fun `sortByWins saves state`() {
        viewModel.sortByWins()
        assertThat(viewModel.sortBy).isEqualTo(TeamsViewModel.SortBy.WINS)
    }

    @Test
    fun `sortByLosses saves state`() {
        viewModel.sortByLosses()
        assertThat(viewModel.sortBy).isEqualTo(TeamsViewModel.SortBy.LOSSES)
    }


}