package com.example.circularplayground.data

import com.example.circularplayground.data.model.AccountStatus
import com.example.circularplayground.data.model.CoachingSummary
import com.example.circularplayground.data.model.CreditInfo
import com.example.circularplayground.data.network.dto.AccountStatusDto
import com.example.circularplayground.data.network.dto.CoachingSummaryDto
import com.example.circularplayground.data.network.dto.CreditInfoDto
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NetworkMapperTest {

    @Test
    fun testMapToModel() {
        val mapper = NetworkMapper()

        val dto =
            AccountStatusDto(
                accountIDVStatus = "PASS",
                creditReportInfo = CreditInfoDto(score = 500,
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
                    daysUntilNextReport = 20),
                dashboardStatus = "",
                personaType = "",
                coachingSummary = CoachingSummaryDto(
                    activeTodo = true,
                    activeChat = false,
                    numberOfTodoItems = 0,
                    numberOfCompletedTodoItems = 0,
                    selected = false),
                augmentedCreditScore = null
            )

        val model = mapper.mapToModel(dto)

        assertThat(model).isEqualTo(accountStatus)
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
        daysUntilNextReport = 20
    )

    private val accountStatus = AccountStatus(
        accountIDVStatus = "PASS",
        creditReportInfo = creditInfo,
        dashboardStatus = "",
        personaType = "",
        coachingSummary = CoachingSummary(
            activeTodo = true,
            activeChat = false,
            numberOfTodoItems = 0,
            numberOfCompletedTodoItems = 0,
            selected = false),
        augmentedCreditScore = null
    )
}