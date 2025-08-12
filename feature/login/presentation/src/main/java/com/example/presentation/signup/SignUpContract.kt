package com.example.presentation.signup

import com.patan.core.common.core.CoreState

internal class SignUpContract {
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
        object OnRegisterClicked : Event()
        data class OnRegisterWithGmailClicked(val ctx: android.content.Context) : Event()
    }

    sealed class SideEffect : CoreState.SideEffect {
        object NavigateLogin : SideEffect()
        data class ShowError(val message: String) : SideEffect() }

    object Static {
        const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    }
}
