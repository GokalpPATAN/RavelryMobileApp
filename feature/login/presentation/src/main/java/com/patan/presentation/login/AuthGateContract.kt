package com.patan.presentation.login

sealed interface AuthGateState : UiState {
    data object Checking : AuthGateState
    data object Authenticated : AuthGateState
    data object Unauthenticated : AuthGateState
}