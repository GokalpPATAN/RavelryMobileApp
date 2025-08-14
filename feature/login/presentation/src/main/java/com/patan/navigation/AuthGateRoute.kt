package com.patan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.patan.presentation.login.AuthGateContent
import com.patan.presentation.login.AuthGateState
import com.patan.presentation.login.AuthGateViewModel

@Composable
fun AuthGateRoute(
    onAuthenticated: () -> Unit,
    onUnauthenticated: () -> Unit,
    viewModel: AuthGateViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state) {
        when (state) {
            AuthGateState.Authenticated   -> onAuthenticated()
            AuthGateState.Unauthenticated -> onUnauthenticated()
            AuthGateState.Checking        -> Unit
        }
    }

    AuthGateContent()
}