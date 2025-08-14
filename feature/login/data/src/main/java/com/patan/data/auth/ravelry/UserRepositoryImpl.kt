package com.patan.data.auth.ravelry

import com.patan.domain.auth.model.User
import com.patan.domain.auth.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api: RavelryApi
) : UserRepository {

    override suspend fun getCurrentUser(): User {
        val resp = api.currentUser()
        return resp.user.toDomain()
    }
}