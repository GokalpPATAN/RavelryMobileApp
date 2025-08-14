package com.patan.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patan.domain.auth.usecase.CheckSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthGateViewModel @Inject constructor(
    private val checkSession: CheckSessionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AuthGateState>(AuthGateState.Checking)
    val state: StateFlow<AuthGateState> = _state // istersen _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value =
                if (checkSession()) AuthGateState.Authenticated
                else AuthGateState.Unauthenticated
        }
    }
}