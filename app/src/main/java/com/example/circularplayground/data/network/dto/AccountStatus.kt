package com.example.circularplayground.data.network.dto

import com.google.gson.annotations.SerializedName

data class AccountStatus(
    @SerializedName("accountIDVStatus")
    val accountIDVStatus: String,
    @SerializedName("creditReportInfo")
    val creditReportInfo: CreditInfoDto,
    @SerializedName("dashboardStatus")
    val dashboardStatus: String,
    @SerializedName("personaType")
    val personaType: String,
    @SerializedName("coachingSummary")
    val coachingSummary: CoachingSummaryDto,
    @SerializedName("augmentedCreditScore")
    val augmentedCreditScore: Int?
)