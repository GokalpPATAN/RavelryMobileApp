package com.patan.presentation.login

interface UiEvent
interface UiEffect
interface UiState
class LoginScreenContract{

    data class LoginState(
        val loading: Boolean = false,
        val success: Boolean = false,
        val error: String? = null
    ) : UiState

    sealed class LoginEvent : UiEvent {
        data object ClickRavelry : LoginEvent()
        data object ClickSignup : LoginEvent()
    }

    sealed class LoginEffect : UiEffect {
        data object NavigateHome : LoginEffect()
        data class ShowError(val message: String) : LoginEffect()
    }
}