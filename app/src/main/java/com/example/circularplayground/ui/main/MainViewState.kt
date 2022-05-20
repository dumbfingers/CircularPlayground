package com.example.circularplayground.ui.main

data class MainViewState(
    val isLoading: Boolean = true,
    val currentScore: Int = 0,
    val totalScore: Int = 0,
    val error: String = "",
    val clientRef: String = "",
)