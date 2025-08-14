package com.patan.feature.login.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.patan.presentation.login.LoginScreenContract
import com.patan.presentation.login.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute(
    onNavigateHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel() // VM'ini daha önce kurmuştuk
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { eff ->
            when (eff) {
                is LoginScreenContract.LoginEffect.NavigateHome -> onNavigateHome()
                is LoginScreenContract.LoginEffect.ShowError    -> { /* snackbar/toast gösterilebilir */ }
            }
        }
    }

    LoginScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}