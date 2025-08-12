package com.example.presentation.signup

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.di.LoginEntryPoint
import com.example.domain.auth.usecase.RequestGoogleIdTokenUseCase
import com.example.presentation.signup.SignUpContract.Event.*
import com.patan.core.common.model.RestResult
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent


@Composable
internal fun SignUpScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()
    val sideEffect = viewModel.sideEffect.collectAsState(initial = null).value

    // Sadece navigation/hata gibi side effect'leri dinle
    LaunchedEffect(sideEffect) {
        when (sideEffect) {
            is SignUpContract.SideEffect.NavigateLogin -> onNavigateToLogin()
            is SignUpContract.SideEffect.ShowError -> { /* Snackbar/Toast opsiyonel */ }
            else -> Unit
        }
    }

    SignUpContent(state = state, onEvent = viewModel::setEvent)
}
