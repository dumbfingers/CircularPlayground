package com.example.circularplayground.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class ReduxViewModel<S>(
    initialState: S
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S>
        get() = _state.asStateFlow()

    fun setState(dispatcher: CoroutineDispatcher = Dispatchers.Main, reducer: S.() -> S) {
        viewModelScope.launch(dispatcher) {
            _state.value = reducer(_state.value)
        }
    }
}