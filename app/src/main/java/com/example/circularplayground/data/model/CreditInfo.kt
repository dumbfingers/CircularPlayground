package com.example.circularplayground.data.model

import com.google.gson.annotations.SerializedName

data class CreditInfo(
    val score: Int,
    val scoreBand: Int,
    val maxScoreValue: Int,
    val minScoreValue: Int,
    val monthsSinceLastDefaulted: Int,
    val hasEverDefaulted: Boolean,
    val monthsSinceLastDelinquent: Int,
    val hasEverBeenDelinquent: Boolean,
    val percentageCreditUsed: Int,
    val percentageCreditUsedDirectionFlag: Int,
    val changedScore: Int,
    val currentShortTermDebt: Long,
    val currentShortTermNonPromotionalDebt: Long,
    val currentShortTermCreditLimit: Long?,
    val currentShortTermCreditUtilisation: Int?,
    val changeInShortTermDebt: Long?,
    val currentLongTermDebt: Long,
    val currentLongTermNonPromotionalDebt: Long,
    val currentLongTermCreditLimit: Long?,
    val currentLongTermCreditUtilisation: Int?,
    val changeInLongTermDebt: Long?,
    val numPositiveScoreFactors: Int,
    val numNegativeScoreFactors: Int,
    val equifaxScoreBand: Int,
    val equifaxScoreBandDescription: String,
    val daysUntilNextReport: Int,
)
