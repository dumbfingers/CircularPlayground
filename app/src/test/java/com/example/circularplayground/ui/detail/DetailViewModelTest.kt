package com.example.circularplayground.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.circularplayground.CoroutineTestRule
import com.example.circularplayground.data.DataRepository
import com.example.circularplayground.data.model.DetailData
import com.example.circularplayground.utils.CoroutineDispatchers
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: DetailViewModel
    private val repository: DataRepository = mockk(relaxed = true, relaxUnitFun = true)
    private val dispatchers = CoroutineDispatchers(
        io = coroutineTestRule.dispatcher,
        main = coroutineTestRule.dispatcher,
        default = coroutineTestRule.dispatcher
    )

    private fun setup(data: DetailData) {
        coEvery { repository.getDetailData() }.returns(
            flowOf(data),
        )

        viewModel = DetailViewModel(
            dispatchers = dispatchers,
            dataRepository = repository
        )
    }

    @Test
    fun when_init_verify_repository_getDetailData_is_invoked() {
        setup(
            DetailData(
                currentShortTermUtilisation = null,
                changeInShortTerm = null,
                currentLongTermUtilisation = null,
                changeInLongTerm = null,
                daysUntilNextReport = null,
            )
        )
        coVerify { repository.getDetailData() }
    }

    @Test
    fun when_init_verify_view_state_is_set_from_getDetailData() {
        val detailData = DetailData(
            currentShortTermUtilisation = 45,
            changeInShortTerm = 589,
            currentLongTermUtilisation = null,
            changeInLongTerm = -900,
            daysUntilNextReport = 28,
        )
        setup(detailData)
        coEvery { repository.getDetailData() }.returns(flowOf(detailData))
        runTest {
            viewModel.state.test {
                awaitItem().apply {
                    assertThat(currentShortTermUtilisation).isEqualTo(detailData.currentShortTermUtilisation)
                    assertThat(changeInShortTerm).isEqualTo(detailData.changeInShortTerm)
                    assertThat(currentLongTermUtilisation).isEqualTo(detailData.currentLongTermUtilisation)
                    assertThat(changeInLongTerm).isEqualTo(detailData.changeInLongTerm)
                    assertThat(daysUntilNextReport).isEqualTo(detailData.daysUntilNextReport)
                }
            }
        }
    }
}
