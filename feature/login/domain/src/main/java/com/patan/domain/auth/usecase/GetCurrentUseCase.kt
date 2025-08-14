package com.patan.domain.auth.usecase

import com.patan.domain.auth.model.User
import com.patan.domain.auth.repository.UserRepository
import javax.inject.Inject

class GetCurrentUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(): User = repo.getCurrentUser()
}