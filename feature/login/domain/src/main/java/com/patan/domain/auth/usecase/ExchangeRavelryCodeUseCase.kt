package com.patan.domain.auth.usecase

import com.patan.domain.auth.model.AuthResult
import com.patan.domain.auth.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExchangeRavelryCodeUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    operator fun invoke(): Flow<AuthResult> = repo.awaitAndExchange()
}