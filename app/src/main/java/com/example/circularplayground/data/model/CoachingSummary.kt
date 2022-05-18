package com.example.circularplayground.data.model

import com.google.gson.annotations.SerializedName

data class CoachingSummary(
    val activeTodo: Boolean,
    val activeChat: Boolean,
    val numberOfTodoItems: Int,
    val numberOfCompletedTodoItems: Int,
    val selected: Boolean,
)
