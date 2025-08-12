package com.example.domain.auth.repository

import com.patan.core.common.model.RestResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun loginWithEmail(email: String, password: String): Flow<RestResult<Unit>>
    suspend fun loginWithGmail(idToken: String): Flow<RestResult<Unit>>
    suspend fun register(email: String, password: String): Flow<RestResult<Unit>>
    suspend fun forgotPassword(email: String): Flow<RestResult<Unit>>
    suspend fun logout()
}
