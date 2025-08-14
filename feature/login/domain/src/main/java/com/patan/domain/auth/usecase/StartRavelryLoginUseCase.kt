package com.patan.domain.auth.usecase

import com.patan.domain.auth.repository.AuthRepository
import javax.inject.Inject

class StartRavelryLoginUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() = repo.startLogin()
}