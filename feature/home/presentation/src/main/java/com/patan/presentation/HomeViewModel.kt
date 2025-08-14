package com.example.presentation

import com.patan.core.common.core.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor():
    CoreViewModel<HomeScreenContract.State, HomeScreenContract.SideEffect, HomeScreenContract.Event>(
        HomeScreenContract.State(isLoading = false)
    ) {
    override fun setEvent(event: HomeScreenContract.Event) {

    }
}