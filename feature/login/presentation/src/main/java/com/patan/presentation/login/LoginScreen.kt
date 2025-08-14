package com.patan.presentation.login

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.patan.feature.login.presentation.login.LoginScreenContent


// LoginScreen.kt
@Composable
internal fun LoginScreen(
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val sideEffect = viewModel.effect.collectAsState(initial = null).value
    val context = LocalContext.current
    val activity = context.asActivity()

    LaunchedEffect(sideEffect) {
        when (sideEffect) {
            is LoginScreenContract.LoginEffect.NavigateHome -> onNavigateToHome()
            is LoginScreenContract.LoginEffect.ShowError -> println("Merhaba")
            else -> println("Merhaba")
        }
    }

    LoginScreenContent(state = state, onEvent = viewModel::onEvent)
}



/** Context → Activity çözümleyici (senin mevcut helper’ın). */
private fun Context.asActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.asActivity()
    else -> null
}