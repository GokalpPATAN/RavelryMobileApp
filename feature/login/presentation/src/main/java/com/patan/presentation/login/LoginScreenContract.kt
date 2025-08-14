package com.example.presentation.login

import com.patan.core.common.core.CoreState

internal class LoginScreenContract {

    data class State(
        override val isLoading: Boolean,
        val email: String = "",
        val password: String = "",
        val isSuccess: Boolean = false,
        val errorMessage: String? = null
    ) : CoreState.ViewState

    sealed class Event : CoreState.Event {
        data class OnEmailChanged(val value: String) : Event()
        data class OnPasswordChanged(val value: String) : Event()
        object OnLoginClicked : Event()
        object OnRegisterClicked : Event()
        data class OnLoginWithGmailClicked(val ctx: android.content.Context) : Event()

    }

    sealed class SideEffect : CoreState.SideEffect {
        object NavigateToHome : SideEffect()
        object NavigateToRegister : SideEffect()
        data class ShowError(val message: String) : SideEffect()
    }

    object Static {
        const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    }
}
