package com.patan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.patan.presentation.login.HomeContract
import com.patan.presentation.login.HomeScreen
import com.patan.presentation.login.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeRoute(
    onNavigateLogin: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // Sadece navigasyonla ilgili efektleri burada yakala
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { eff ->
            when (eff) {
                is HomeContract.HomeEffect.NavigateLogin -> onNavigateLogin()
                is HomeContract.HomeEffect.ShowMessage   -> { /* HomeScreen içindeki snackbar host gösterecek */ }
            }
        }
    }

    // UI kabuğu (AppBar/Snackbar) + içerik
    HomeScreen(
        state = state,
        onEvent = viewModel::onEvent,
        messages = viewModel.effect // snackbar için ilet
    )
}