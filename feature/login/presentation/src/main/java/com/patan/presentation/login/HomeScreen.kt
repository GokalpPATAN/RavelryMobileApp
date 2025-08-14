package com.patan.presentation.login

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeContract.HomeState,
    onEvent: (HomeContract.HomeEvent) -> Unit,
    messages: Flow<HomeContract.HomeEffect>?, // Route'tan geçireceğiz
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(messages) {
        messages?.collect { eff ->
            when (eff) {
                is HomeContract.HomeEffect.ShowMessage -> snackbarHostState.showSnackbar(eff.msg)
                else -> Unit // NavigateLogin burada değil, Route'ta ele alınır
            }
        }
    }

    HomeContent(
        state = state,
        onEvent = onEvent
    )
}