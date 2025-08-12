package com.example.presentation.login

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.login.LoginScreenContract.Event.*
import com.example.presentation.signup.SignUpContent
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetGoogleIdOption.*
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.patan.core.common.model.RestResult
import com.patan.feature.login.presentation.R
import dagger.hilt.android.EntryPointAccessors


// LoginScreen.kt
@Composable
internal fun LoginScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val sideEffect = viewModel.sideEffect.collectAsState(initial = null).value
    val context = LocalContext.current
    val activity = context.asActivity()

    LaunchedEffect(sideEffect) {
        when (sideEffect) {
            is LoginScreenContract.SideEffect.NavigateToHome -> onNavigateToHome()

            LoginScreenContract.SideEffect.NavigateToRegister -> onNavigateToRegister()
            is LoginScreenContract.SideEffect.ShowError -> println("Merhaba")
            else -> println("Merhaba")
        }
    }

    LoginScreenContent(state = state, onEvent = viewModel::setEvent)
}



/** Context → Activity çözümleyici (senin mevcut helper’ın). */
private fun Context.asActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.asActivity()
    else -> null
}