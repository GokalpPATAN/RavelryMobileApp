package com.patan.data.auth.repository

import com.patan.data.auth.ravelry.RavelryApi
import com.patan.data.auth.storage.TokenStore
import com.patan.domain.session.SessionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepositoryImpl @Inject constructor(
    private val api: RavelryApi,
    private val tokenStore: TokenStore
) : SessionRepository {
    override suspend fun isLoggedIn(): Boolean {
        val token = tokenStore.getAccess() ?: return false
        return try { api.currentUser(); true } catch (_: Throwable) { false }
    }
}