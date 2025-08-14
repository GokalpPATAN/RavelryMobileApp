package com.patan.data.auth.di

import com.patan.data.auth.ravelry.UserRepositoryImpl
import com.patan.data.auth.repository.AuthRepositoryImpl
import com.patan.data.auth.repository.SessionRepositoryImpl
import com.patan.domain.auth.repository.AuthRepository
import com.patan.domain.auth.repository.UserRepository
import com.patan.domain.session.SessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainBindings {

    @Binds @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds @Singleton
    abstract fun bindSessionRepository(impl: SessionRepositoryImpl): SessionRepository

    @Binds @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}