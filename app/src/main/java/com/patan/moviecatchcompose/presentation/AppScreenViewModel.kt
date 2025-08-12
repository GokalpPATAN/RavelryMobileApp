package com.patan.moviecatchcompose.presentation

import androidx.lifecycle.viewModelScope
import com.patan.core.common.core.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AppScreenViewModel @Inject constructor() : CoreViewModel<AppScreenContract.State, AppScreenContract.SideEffect, AppScreenContract.Event>(
    initialState = AppScreenContract.State(
        isLoading = false,
    ),
) {

    init {
        viewModelScope.launch {
            delay(AppScreenContract.Static.KEEP_SPLASH_SCREEN_DELAY)
            updateState { it.copy(keepSplashScreenOn = false) }
        }
    }

    override fun setEvent(event: AppScreenContract.Event) { }
}