package com.example.presentation.signup

import androidx.lifecycle.viewModelScope
import com.example.domain.auth.usecase.LoginWithGmailUseCase
import com.example.domain.auth.usecase.RegisterUseCase
import com.patan.core.common.core.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SignUpViewModel @Inject constructor(
    private val signUpUseCase: RegisterUseCase,
    private val loginWithGmailUseCase: LoginWithGmailUseCase,
    private val requestGoogleIdTokenUseCase: com.example.domain.auth.usecase.RequestGoogleIdTokenUseCase
) : CoreViewModel<SignUpContract.State, SignUpContract.SideEffect, SignUpContract.Event>(
    SignUpContract.State(isLoading = false)
) {

    override fun setEvent(event: SignUpContract.Event) {
        when (event) {
            is SignUpContract.Event.OnEmailChanged ->
                updateState { it.copy(email = event.value) }

            is SignUpContract.Event.OnPasswordChanged ->
                updateState { it.copy(password = event.value) }

            SignUpContract.Event.OnRegisterClicked ->
                register()

            is SignUpContract.Event.OnRegisterWithGmailClicked ->
                registerWithGoogle(event.ctx)
        }
    }

    private fun register() {
        viewModelScope.launch {
            signUpUseCase(state.value.email, state.value.password)
                .requester
                .onLoading { updateState { it.copy(isLoading = true, errorMessage = null) } }
                .onError { error ->
                    updateState { it.copy(isLoading = false, errorMessage = error.message) }
                    setSideEffect(SignUpContract.SideEffect.ShowError(error.message))
                }
                .callWithSuccess {
                    updateState { it.copy(isLoading = false, isSuccess = true) }
                    setSideEffect(SignUpContract.SideEffect.NavigateLogin)
                }
        }
    }

    private fun registerWithGoogle(ctx: android.content.Context) {
        viewModelScope.launch {
            // 1) Token iste (Flow<RestResult<String>>)
            requestGoogleIdTokenUseCase(ctx)
                .requester
                .onLoading {
                    updateState { it.copy(isLoading = true, errorMessage = null) }
                }
                .onError { error ->
                    updateState { it.copy(isLoading = false, errorMessage = error.message) }
                    setSideEffect(SignUpContract.SideEffect.ShowError(error.message))
                }
                .callWithSuccess { token ->
                    // 2) Token geldiyse Firebase'e Google ile login
                    loginWithGmailUseCase(token)
                        .requester
                        .onLoading {
                            // zaten loading true; idempotent kalsın
                            updateState { it.copy(isLoading = true) }
                        }
                        .onError { error ->
                            updateState { it.copy(isLoading = false, errorMessage = error.message) }
                            setSideEffect(SignUpContract.SideEffect.ShowError(error.message))
                        }
                        .callWithSuccess {
                            updateState { it.copy(isLoading = false, isSuccess = true) }
                            setSideEffect(SignUpContract.SideEffect.NavigateLogin)
                        }
                }
        }
    }
}
