package com.patan.domain.auth.usecase

import com.patan.domain.session.SessionRepository
import javax.inject.Inject

class CheckSessionUseCase @Inject constructor(
    private val repo: SessionRepository
) { suspend operator fun invoke(): Boolean = repo.isLoggedIn() }