package com.patan.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patan.domain.auth.model.AuthResult
import com.patan.domain.auth.usecase.ExchangeRavelryCodeUseCase
import com.patan.domain.auth.usecase.StartRavelryLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val startLogin: StartRavelryLoginUseCase,
    private val exchange: ExchangeRavelryCodeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginScreenContract.LoginState())
    val state: StateFlow<LoginScreenContract.LoginState> = _state

    private val _effect = Channel<LoginScreenContract.LoginEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: LoginScreenContract.LoginEvent) = when(event){
        LoginScreenContract.LoginEvent.ClickRavelry -> launchLogin()
        LoginScreenContract.LoginEvent.ClickSignup  -> openSignup()
    }

    private fun launchLogin() = viewModelScope.launch {
        _state.update { it.copy(loading = true, error = null) }
        startLogin()                       // Custom Tab açılır
        exchange().collect { result ->
            when (result) {
                is AuthResult.Loading ->
                    _state.update { it.copy(loading = true) }
                is AuthResult.Success -> {
                    _state.update { it.copy(loading = false, success = true) }
                    _effect.send(LoginScreenContract.LoginEffect.NavigateHome)
                }
                is AuthResult.Error -> {
                    _state.update { it.copy(loading = false, error = result.message) }
                    _effect.send(LoginScreenContract.LoginEffect.ShowError(result.message))
                }
            }
        }
    }

    private fun openSignup() { /* dış tarayıcı açma işini UI tarafında yaptık */ }
}