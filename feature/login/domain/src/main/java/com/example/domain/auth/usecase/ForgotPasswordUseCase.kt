package com.example.domain.auth.usecase

import com.example.domain.auth.repository.AuthRepository
import com.patan.core.common.model.RestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: String): Flow<RestResult<Unit>> = flow {
        emit(RestResult.Loading)
        emitAll(authRepository.forgotPassword(email))
    }
}