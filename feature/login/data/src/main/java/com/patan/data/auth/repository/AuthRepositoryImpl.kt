package com.patan.data.auth.repository

import android.util.Base64
import com.patan.data.auth.ravelry.RavelryAuthManager
import com.patan.data.auth.ravelry.RavelryOAuthService
import com.patan.data.auth.settings.RavelrySettings
import com.patan.data.auth.storage.TokenStore
import com.patan.domain.auth.model.AuthResult
import com.patan.domain.auth.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authManager: RavelryAuthManager,
    private val oauth: RavelryOAuthService,
    private val tokenStore: TokenStore,
    private val settings: RavelrySettings
) : AuthRepository {

    override suspend fun startLogin() = authManager.startLogin()

    override fun awaitAndExchange(): Flow<AuthResult> = flow {
        emit(AuthResult.Loading)
        val codeRes = authManager.codeFlow.first()
        val code = codeRes.getOrElse { e -> throw e }

        val basic = "Basic " + Base64.encodeToString(
            "${settings.clientId}:${settings.clientSecret}".toByteArray(),
            Base64.NO_WRAP
        )
        val token = oauth.exchangeCode(
            basic = basic,
            code = code,
            redirectUri = settings.baseUrl
        )
        tokenStore.save(token)
        emit(AuthResult.Success)
    }

    override suspend fun logout() = tokenStore.clear()
}