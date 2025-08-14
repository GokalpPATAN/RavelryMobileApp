package com.patan.domain.auth.repository

import com.patan.domain.auth.model.User

interface UserRepository {
    suspend fun getCurrentUser(): User
}