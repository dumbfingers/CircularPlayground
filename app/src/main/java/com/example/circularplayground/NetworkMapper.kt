package com.example.circularplayground

import com.example.circularplayground.data.network.dto.AccountStatusDto
import com.example.circularplayground.data.network.dto.CoachingSummaryDto
import com.example.circularplayground.data.network.dto.CreditInfoDto
import javax.inject.Inject

class NetworkMapper @Inject constructor() {

    fun mapToModel(dto: AccountStatusDto): AccountStatus {
        return AccountStatus(
            accountIDVStatus = dto.accountIDVStatus,
            creditReportInfo = mapToModel(dto.creditReportInfo),
            dashboardStatus = dto.dashboardStatus,
            personaType = dto.personaType,
            coachingSummary = mapToModel(dto.coachingSummary),
            augmentedCreditScore = dto.augmentedCreditScore
        )
    }

    private fun mapToModel(dto: CreditInfoDto): CreditInfo {
        return dto.run {
            CreditInfo(
                score = score,
                scoreBand = scoreBand,
                maxScoreValue = maxScoreValue,
                minScoreValue = minScoreValue,
                monthsSinceLastDefaulted = monthsSinceLastDefaulted,
                hasEverDefaulted = hasEverDefaulted,
                monthsSinceLastDelinquent = monthsSinceLastDelinquent,
                hasEverBeenDelinquent = hasEverBeenDelinquent,
                percentageCreditUsed = percentageCreditUsed,
                percentageCreditUsedDirectionFlag = percentageCreditUsedDirectionFlag,
                changedScore = changedScore,
                currentShortTermDebt = currentShortTermDebt,
                currentShortTermNonPromotionalDebt = currentShortTermNonPromotionalDebt,
                currentShortTermCreditLimit = currentShortTermCreditLimit,
                currentShortTermCreditUtilisation = currentShortTermCreditUtilisation,
                changeInShortTermDebt = changeInShortTermDebt,
                currentLongTermDebt = currentLongTermDebt,
                currentLongTermNonPromotionalDebt = currentLongTermNonPromotionalDebt,
                currentLongTermCreditLimit = currentLongTermCreditLimit,
                currentLongTermCreditUtilisation = currentLongTermCreditUtilisation,
                changeInLongTermDebt = changeInLongTermDebt,
                numPositiveScoreFactors = numPositiveScoreFactors,
                numNegativeScoreFactors = numNegativeScoreFactors,
                equifaxScoreBand = equifaxScoreBand,
                equifaxScoreBandDescription = equifaxScoreBandDescription,
                daysUntilNextReport = daysUntilNextReport,
            )
        }
    }

    private fun mapToModel(dto: CoachingSummaryDto): CoachingSummary {
        return dto.run {
            CoachingSummary(
                activeTodo = activeTodo,
                activeChat = activeChat,
                numberOfTodoItems = numberOfTodoItems,
                numberOfCompletedTodoItems = numberOfCompletedTodoItems,
                selected = selected
            )
        }
    }
}