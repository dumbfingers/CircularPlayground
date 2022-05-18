package com.example.circularplayground.ui

data class DetailViewState(
    val currentShortTermUtilisation: Int? = null,
    val changeInShortTerm: Long? = null,
    val currentLongTermUtilisation: Int? = null,
    val changeInLongTerm: Long? = null,
    val daysUntilNextReport: Int? = null,
)
