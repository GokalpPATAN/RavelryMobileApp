package com.patan.presentation

import com.patan.core.common.core.CoreState

internal class HomeScreenContract {
    data class State(
        override val isLoading: Boolean
    ) : CoreState.ViewState

    sealed class Event : CoreState.Event {

    }

    sealed class SideEffect : CoreState.SideEffect {
    }

    object Static {

    }
}