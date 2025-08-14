package com.patan.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val sideEffect = viewModel.sideEffect.collectAsState(initial = null).value

    HomeScreenContent(state = state, onEvent = viewModel::setEvent)
}