package com.example.circularplayground.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.circularplayground.CoroutineTestRule
import com.example.circularplayground.data.DataRepository
import com.example.circularplayground.data.Resource
import com.example.circularplayground.data.model.AccountStatus
import com.example.circularplayground.data.model.CoachingSummary
import com.example.circularplayground.data.model.CreditInfo
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
class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: MainViewModel
    private val repository: DataRepository = mockk(relaxed = true, relaxUnitFun = true)
    private val dispatchers = CoroutineDispatchers(
        io = coroutineTestRule.dispatcher,
        main = coroutineTestRule.dispatcher,
        default = coroutineTestRule.dispatcher
    )

    @Before
    fun setup() {
        coEvery { repository.getAccountStatus() }.returns(flowOf(Resource(Resource.Status.LOADING)))
        viewModel = MainViewModel(
            dataRepository = repository,
            dispatchers = dispatchers
        )
    }

    @Test
    fun when_refresh_verify_getAccountStatus_from_repository_is_invoked() {
        coVerify { repository.getAccountStatus() }
    }

    @Test
    fun given_getAccountStatus_returns_LOADING_verify_isLoading_isTrue_error_isEmpty() {
        coEvery { repository.getAccountStatus() }.returns(flowOf(Resource(Resource.Status.LOADING)))
        runTest {
            viewModel.refresh()
            viewModel.state.test {
                val state = awaitItem()
                assertThat(state.isLoading).isTrue()
                assertThat(state.error).isEmpty()
            }
        }
    }

    @Test
    fun given_getAccountStatus_returns_SUCCESS_verify_isLoading_isFalse_error_isEmpty_currentScore_and_totalScore_are_set() {
        coEvery { repository.getAccountStatus() }.returns(
            flowOf(
                Resource(Resource.Status.SUCCESS, data = accountStatus)
            )
        )

        runTest {
            viewModel.refresh()
            viewModel.state.test {
                val state = awaitItem()
                assertThat(state.isLoading).isFalse()
                assertThat(state.error).isEmpty()
                assertThat(state.currentScore).isEqualTo(accountStatus.creditReportInfo.score)
                assertThat(state.totalScore).isEqualTo(accountStatus.creditReportInfo.maxScoreValue)
            }
        }
    }

    @Test
    fun given_getAccountStatus_returns_ERROR_verify_isLoading_isFalse_error_is_set_from_resource() {
        val error = Throwable("message")
        coEvery { repository.getAccountStatus() }.returns(
            flowOf(
                Resource(Resource.Status.ERROR, error = error)
            )
        )

        viewModel.refresh()
        runTest {
            viewModel.state.test {
                val state = awaitItem()
                assertThat(state.isLoading).isFalse()
                assertThat(state.error).isEqualTo(error.message)
            }
        }
    }

    private val creditInfo = CreditInfo(
        score = 500,
        scoreBand = 0,
        maxScoreValue = 700,
        minScoreValue = 0,
        monthsSinceLastDefaulted = 0,
        hasEverDefaulted = false,
        monthsSinceLastDelinquent = 0,
        hasEverBeenDelinquent = false,
        percentageCreditUsed = 0,
        percentageCreditUsedDirectionFlag = 0,
        changedScore = 0,
        currentShortTermDebt = 0,
        currentShortTermNonPromotionalDebt = 0,
        currentShortTermCreditLimit = 0,
        currentShortTermCreditUtilisation = null,
        changeInShortTermDebt = null,
        currentLongTermDebt = 0,
        currentLongTermNonPromotionalDebt = 0,
        currentLongTermCreditLimit = null,
        currentLongTermCreditUtilisation = null,
        changeInLongTermDebt = null,
        numPositiveScoreFactors = 0,
        numNegativeScoreFactors = 0,
        equifaxScoreBand = 0,
        equifaxScoreBandDescription = "",
        daysUntilNextReport = 0
    )

    private val accountStatus = AccountStatus(
        accountIDVStatus = "PASS",
        creditReportInfo = creditInfo,
        dashboardStatus = "",
        personaType = "",
        coachingSummary = CoachingSummary(
            activeTodo = false,
            activeChat = false,
            numberOfTodoItems = 0,
            numberOfCompletedTodoItems = 0,
            selected = false),
        augmentedCreditScore = null
    )
}
