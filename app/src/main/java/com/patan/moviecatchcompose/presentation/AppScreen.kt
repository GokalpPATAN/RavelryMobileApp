package com.patan.moviecatchcompose.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.patan.moviecatchcompose.ui.theme.MovieCatchComposeTheme

@Composable
internal fun AppScreen(viewModel: AppScreenViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navigationController = rememberNavController()

    if (!state.keepSplashScreenOn) {
        Scaffold(
            navigationController = navigationController,
            state = state,
        )
    }
}

@Composable
private fun Scaffold(
    navigationController: NavHostController,
    state: AppScreenContract.State,
) {
    MovieCatchComposeTheme {
        AppScreenContent(
            navigationController = navigationController,
            state = state,
        )
    }
}
