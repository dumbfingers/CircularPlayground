package com.example.circularplayground

data class AccountStatus(
    val accountIDVStatus: String,
    val creditReportInfo: CreditInfo,
    val dashboardStatus: String,
    val personaType: String,
    val coachingSummary: CoachingSummary,
    val augmentedCreditScore: Int?,
)
