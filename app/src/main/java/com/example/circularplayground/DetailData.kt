package com.example.circularplayground

data class DetailData(
    val currentShortTermUtilisation: Int?,
    val changeInShortTerm: Long?,
    val currentLongTermUtilisation: Int?,
    val changeInLongTerm: Long?,
    val daysUntilNextReport: Int?
)