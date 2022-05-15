package com.example.circularplayground.data.network.dto

import com.google.gson.annotations.SerializedName

data class CoachingSummaryDto(
    @SerializedName("activeTodo")
    val activeTodo: Boolean,
    @SerializedName("activeChat")
    val activeChat: Boolean,
    @SerializedName("numberOfTodoItems")
    val numberOfTodoItems: Int,
    @SerializedName("numberOfCompletedTodoItems")
    val numberOfCompletedTodoItems: Int,
    @SerializedName("selected")
    val selected: Boolean,
)