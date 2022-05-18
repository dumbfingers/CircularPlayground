package com.example.circularplayground

data class MainViewState(
    val isLoading: Boolean = false,
    val currentScore: Int = 0,
    val totalScore: Int = 0,
    val error: String = "",
    val clientRef: String = "",
)