package com.patan.moviecatchcompose.presentation

import com.patan.core.common.core.CoreState

internal class AppScreenContract {

    data class State(
        override val isLoading: Boolean,
        val keepSplashScreenOn: Boolean = true,
    ) : CoreState.ViewState

    sealed class SideEffect : CoreState.SideEffect

    sealed class Event : CoreState.Event

    object Static {
        const val KEEP_SPLASH_SCREEN_DELAY: Long = 1000L
    }
}