package com.example.circularplayground.data

data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val error: Throwable? = null,
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}