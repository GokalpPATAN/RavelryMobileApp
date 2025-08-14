package com.patan.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patan.domain.auth.usecase.GetCurrentUseCase
import com.patan.domain.auth.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUseCase,
    private val logout: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeContract.HomeState())
    val state: StateFlow<HomeContract.HomeState> = _state.asStateFlow()

    private val _effect = Channel<HomeContract.HomeEffect>(Channel.BUFFERED)
    val effect: Flow<HomeContract.HomeEffect> = _effect.receiveAsFlow()

    init { refresh() }

    fun onEvent(event: HomeContract.HomeEvent) = when (event) {
        HomeContract.HomeEvent.Refresh -> refresh()
        HomeContract.HomeEvent.ClickLogout -> onLogout()
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            try {
                // GetCurrentUserUseCase kullanıcıyı döndürmeli (en az username alanı)
                val user = getCurrentUser() // domain: suspend operator fun invoke(): User
                _state.update { it.copy(loading = false, username = user.username, error = null) }
            } catch (t: Throwable) {
                _state.update { it.copy(loading = false, error = (t.message ?: "Bir şeyler ters gitti")) }
            }
        }
    }

    private fun onLogout() {
        viewModelScope.launch {
            try {
                logout() // domain: suspend operator fun invoke()
                _effect.send(HomeContract.HomeEffect.NavigateLogin)
            } catch (t: Throwable) {
                _effect.send(HomeContract.HomeEffect.ShowMessage(t.message ?: "Çıkış başarısız"))
            }
        }
    }
}