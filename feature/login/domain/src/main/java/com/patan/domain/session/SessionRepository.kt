package com.patan.domain.session

interface SessionRepository {
    suspend fun isLoggedIn(): Boolean
}