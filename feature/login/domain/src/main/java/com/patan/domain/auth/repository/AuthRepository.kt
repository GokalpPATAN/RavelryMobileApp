package com.patan.domain.auth.repository

import com.patan.domain.auth.model.AuthResult
import kotlinx.coroutines.flow.Flow
interface AuthRepository {
    /** Yetkilendirme sayfasını açar (Custom Tab). */
    suspend fun startLogin()

    /** Redirect geldikten sonra code→token değişimini yapar. */
    fun awaitAndExchange(): Flow<AuthResult>

    suspend fun logout()
}
