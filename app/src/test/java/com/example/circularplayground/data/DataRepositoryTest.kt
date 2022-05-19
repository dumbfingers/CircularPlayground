package com.example.circularplayground.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import com.example.circularplayground.CoroutineTestRule
import com.example.circularplayground.data.model.AccountStatus
import com.example.circularplayground.data.model.DetailData
import com.example.circularplayground.data.network.CircularService
import com.example.circularplayground.data.network.dto.AccountStatusDto
import com.example.circularplayground.data.network.dto.CoachingSummaryDto
import com.example.circularplayground.data.network.dto.CreditInfoDto
import com.example.circularplayground.utils.Constants
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.createTestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DataRepositoryTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val circularService = mockk<CircularService>()
    private val mapper = NetworkMapper()
    private val dataStore = mockk<DataStore<Preferences>>(relaxed = true)
    private lateinit var repository: DataRepository

    @Before
    fun setUp() {
        repository = DataRepository(
            circularService = circularService,
            mapper = mapper,
            dataStore = dataStore
        )
    }

    @Test
    fun getAccountStatus_returns_flowOf_loading() {
        coEvery { circularService.getAccountStatus() }.returns(Response.success(fakeDto))
        coJustRun { dataStore.edit { } }
        runTest {
            val actual = mutableListOf<Resource<AccountStatus>>()
            repository.getAccountStatus().toCollection(actual)
            val expected = mutableListOf<Resource<AccountStatus>>()
            flowOf(
                Resource(Resource.Status.LOADING, data = null)
            ).toCollection(expected)
            assertThat(actual[0]).isEqualTo(expected[0])
        }
    }

    @Test
    fun getAccountStatus_returns_flowOf_success_when_getAccountStatus_in_circularService_returns_non_null_and_response_is_success() {
        coEvery { circularService.getAccountStatus() }.returns(Response.success(fakeDto))
        coJustRun { dataStore.edit { } }
        runTest {
            val actual = mutableListOf<Resource<AccountStatus>>()
            repository.getAccountStatus().toCollection(actual)
            val expected = mutableListOf<Resource<AccountStatus>>()
            flowOf(
                Resource(Resource.Status.LOADING),
                Resource(
                    status = Resource.Status.SUCCESS,
                    data = mapper.mapToModel(fakeDto)
                )
            ).toCollection(expected)
            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun getAccountStatus_returns_flowOf_error_when_getAccountStatus_in_circularService_returns_null() {
        coEvery { circularService.getAccountStatus() }.returns(Response.success(null))
        coJustRun { dataStore.edit { } }
        runTest {
            val actual = mutableListOf<Resource<AccountStatus>>()
            repository.getAccountStatus().toCollection(actual)
            val expected = mutableListOf<Resource<AccountStatus>>()
            flowOf(
                Resource(Resource.Status.LOADING),
                Resource(
                    status = Resource.Status.ERROR,
                    error = Throwable("Error: 200"),
                    data = null
                )
            ).toCollection(expected)
            assertThat(actual[1].status).isEqualTo(expected[1].status)
            assertThat(actual[1].error?.message).isEqualTo(expected[1].error?.message)
        }
    }

    @Test
    fun getAccountStatus_returns_flowOf_error_when_getAccountStatus_in_circularService_returns_non_successful() {
        coEvery { circularService.getAccountStatus() }.returns(Response.error(404,
            ResponseBody.create(null, "")))
        coJustRun { dataStore.edit { } }
        runTest {
            val actual = mutableListOf<Resource<AccountStatus>>()
            repository.getAccountStatus().toCollection(actual)
            val expected = mutableListOf<Resource<AccountStatus>>()
            flowOf(
                Resource(Resource.Status.LOADING),
                Resource(
                    status = Resource.Status.ERROR,
                    error = Throwable("Error: 404"),
                    data = null
                )
            ).toCollection(expected)
            assertThat(actual[1].status).isEqualTo(expected[1].status)
            assertThat(actual[1].error?.message).isEqualTo(expected[1].error?.message)
        }
    }

    @Test
    fun getDetailData_returns_DetailData_from_dataStore() {
        val expected = DetailData(currentShortTermUtilisation = 44,
            changeInShortTerm = 999,
            currentLongTermUtilisation = 0,
            changeInLongTerm = 100,
            daysUntilNextReport = 28
        )
        every { dataStore.data }.returns(
            flowOf(
                preferencesOf(
                    Constants.KEY_CURRENT_SHORT_TERM_UTIL to 44,
                    Constants.KEY_CHANGE_IN_SHORT_TERM to 999,
                    Constants.KEY_CURRENT_LONG_TERM_UTIL to 0,
                    Constants.KEY_CHANGE_IN_LONG_TERM to 100,
                    Constants.KEY_DAYS_UNTIL_NEXT_REPORT to 28,
                ),
            ),
        )
        runTest {
            repository.getDetailData().collect { actual ->
                assertThat(actual).isEqualTo(expected)
            }
        }
    }

    private val fakeDto = AccountStatusDto(accountIDVStatus = "",
        creditReportInfo = CreditInfoDto(
            score = 0,
            scoreBand = 0,
            maxScoreValue = 0,
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
            changeInShortTermDebt = 0,
            currentLongTermDebt = 0,
            currentLongTermNonPromotionalDebt = 0,
            currentLongTermCreditLimit = null,
            currentLongTermCreditUtilisation = null,
            changeInLongTermDebt = null,
            numPositiveScoreFactors = 0,
            numNegativeScoreFactors = 0,
            equifaxScoreBand = 0,
            equifaxScoreBandDescription = "",
            daysUntilNextReport = 0),
        dashboardStatus = "",
        personaType = "",
        coachingSummary = CoachingSummaryDto(
            activeTodo = false,
            activeChat = false,
            numberOfTodoItems = 0,
            numberOfCompletedTodoItems = 0,
            selected = false),
        augmentedCreditScore = null
    )
}