package com.example.data.auth.di

import com.example.data.auth.google.GoogleCredentialDataSource
import com.example.domain.auth.repository.GoogleIdTokenProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class IdTokenModule {

    @Binds
    @Singleton
    abstract fun bindGoogleIdTokenProvider(
        impl: GoogleCredentialDataSource
    ): GoogleIdTokenProvider
}