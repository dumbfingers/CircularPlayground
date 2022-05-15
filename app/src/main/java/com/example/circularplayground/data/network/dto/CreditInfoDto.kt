package com.example.circularplayground.data.network.dto

import com.google.gson.annotations.SerializedName

data class CreditInfoDto(
    @SerializedName("score")
    val score: Int,
    @SerializedName("scoreBand")
    val scoreBand: Int,
    @SerializedName("maxScoreValue")
    val maxScoreValue: Int,
    @SerializedName("minScoreValue")
    val minScoreValue: Int,
    @SerializedName("monthsSinceLastDefaulted")
    val monthsSinceLastDefaulted: Int,
    @SerializedName("hasEverDefaulted")
    val hasEverDefaulted: Boolean,
    @SerializedName("monthsSinceLastDelinquent")
    val monthsSinceLastDelinquent: Int,
    @SerializedName("hasEverBeenDelinquent")
    val hasEverBeenDelinquent: Boolean,
    @SerializedName("percentageCreditUsed")
    val percentageCreditUsed: Int,
    @SerializedName("percentageCreditUsedDirectionFlag")
    val percentageCreditUsedDirectionFlag: Int,
    @SerializedName("changedScore")
    val changedScore: Int,
    @SerializedName("currentShortTermDebt")
    val currentShortTermDebt: Long,
    @SerializedName("currentShortTermNonPromotionalDebt")
    val currentShortTermNonPromotionalDebt: Long,
    @SerializedName("currentShortTermCreditLimit")
    val currentShortTermCreditLimit: Long,
    @SerializedName("currentShortTermCreditUtilisation")
    val currentShortTermCreditUtilisation: Long,
    @SerializedName("changeInShortTermDebt")
    val changeInShortTermDebt: Long,
    @SerializedName("currentLongTermDebt")
    val currentLongTermDebt: Long,
    @SerializedName("currentLongTermNonPromotionalDebt")
    val currentLongTermNonPromotionalDebt: Long,
    @SerializedName("currentLongTermCreditLimit")
    val currentLongTermCreditLimit: Long?,
    @SerializedName("currentLongTermCreditUtilisation")
    val currentLongTermCreditUtilisation: Long?,
    @SerializedName("changeInLongTermDebt")
    val changeInLongTermDebt: Long?,
    @SerializedName("numPositiveScoreFactors")
    val numPositiveScoreFactors: Int,
    @SerializedName("numNegativeScoreFactors")
    val numNegativeScoreFactors: Int,
    @SerializedName("equifaxScoreBand")
    val equifaxScoreBand: Int,
    @SerializedName("equifaxScoreBandDescription")
    val equifaxScoreBandDescription: String,
    @SerializedName("daysUntilNextReport")
    val daysUntilNextReport: Int,
)
