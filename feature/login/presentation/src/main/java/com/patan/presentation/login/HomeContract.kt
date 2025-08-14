package com.patan.presentation.login

class HomeContract{
    data class HomeState(
        val loading: Boolean = true,
        val username: String? = null,
        val error: String? = null
    ) : UiState

    sealed class HomeEvent : UiEvent {
        data object Refresh : HomeEvent()
        data object ClickLogout : HomeEvent()
    }

    sealed class HomeEffect : UiEffect {
        data object NavigateLogin : HomeEffect()
        data class ShowMessage(val msg: String) : HomeEffect()
    }
}