package com.example.presentation.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.domain.auth.usecase.LoginWithEmailUseCase
import com.example.domain.auth.usecase.LoginWithGmailUseCase
import com.example.domain.auth.usecase.RequestGoogleIdTokenUseCase
import com.patan.core.common.core.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginWithEmailUseCase,
    private val loginWithGmailUseCase: LoginWithGmailUseCase,
    private val requestGoogleIdToken: RequestGoogleIdTokenUseCase
) : CoreViewModel<LoginScreenContract.State, LoginScreenContract.SideEffect, LoginScreenContract.Event>(
    LoginScreenContract.State(isLoading = false)
) {

    override fun setEvent(event: LoginScreenContract.Event) {
        when (event) {
            is LoginScreenContract.Event.OnEmailChanged ->
                updateState { it.copy(email = event.value) }

            is LoginScreenContract.Event.OnPasswordChanged ->
                updateState { it.copy(password = event.value) }

            LoginScreenContract.Event.OnLoginClicked -> loginWithEmail()

            LoginScreenContract.Event.OnRegisterClicked ->
                setSideEffect(LoginScreenContract.SideEffect.NavigateToRegister)

            is LoginScreenContract.Event.OnLoginWithGmailClicked -> {
                requestIdToken(event.ctx)
            }
        }
    }

    private fun loginWithEmail() {
        viewModelScope.launch {
            loginUseCase(state.value.email, state.value.password)
                .requester
                .onLoading {
                    updateState { it.copy(isLoading = true, errorMessage = null) }
                }
                .onError { error ->
                    updateState { it.copy(isLoading = false, errorMessage = error.message) }
                    setSideEffect(LoginScreenContract.SideEffect.ShowError(error.message))
                }
                .callWithSuccess {
                    updateState { it.copy(isLoading = false, isSuccess = true) }
                    setSideEffect(LoginScreenContract.SideEffect.NavigateToHome)
                }
        }
    }

    private fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            loginWithGmailUseCase(idToken)
                .requester
                .onLoading {
                    // Google akışında zaten loading true idi; yine de idempotent kalsın
                    updateState { it.copy(isLoading = true, errorMessage = null) }
                }
                .onError { error ->
                    updateState { it.copy(isLoading = false, errorMessage = error.message) }
                    setSideEffect(LoginScreenContract.SideEffect.ShowError(error.message))
                }
                .callWithSuccess {
                    updateState { it.copy(isLoading = false, isSuccess = true) }
                    setSideEffect(LoginScreenContract.SideEffect.NavigateToHome)
                }
        }
    }

    private fun requestIdToken(context: Context){
        viewModelScope.launch {
            requestGoogleIdToken(context)
                .requester
                .onLoading {
                    updateState { it.copy(isLoading = true, errorMessage = null) }
                }
                .onError { error ->
                    updateState { it.copy(isLoading = false, errorMessage = error.message) }
                    setSideEffect(LoginScreenContract.SideEffect.ShowError(error.message))
                }
                .callWithSuccess { idToken ->
                    loginWithGoogle(idToken)
                }
        }
    }
}