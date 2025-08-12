package com.patan.core.common.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patan.core.common.model.RestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import requester.createRequesterFlowBuilder

interface CoreViewModelInterface<EVENT:CoreState.Event>{
    fun setEvent(event: EVENT)
}
abstract class CoreViewModel<STATE : CoreState.ViewState, SIDE_EFFECT : CoreState.SideEffect, EVENT : CoreState.Event>(
    private val initialState: STATE,
) : ViewModel(), CoreViewModelInterface<EVENT> {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SIDE_EFFECT>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun STATE.update() {
        updateState(this)
    }

    private fun updateState(newState: STATE) {
        if (newState != _state.value) {
            _state.value = newState
        }
    }

    fun updateState(newState: (STATE) -> STATE) {
        if (newState(state.value) != _state.value) {
            _state.value = newState(state.value)
        }
    }

    fun setSideEffect(effect: SIDE_EFFECT) {
        viewModelScope.launch { _sideEffect.emit(effect) }
    }

    // Requester
    protected val <T> Flow<RestResult<T>>.requester
        get() = createRequesterFlowBuilder(viewModelScope)

    protected val <T> Flow<RestResult<T>>.requesterWithoutWait
        get() = createRequesterFlowBuilder(viewModelScope, false)
}