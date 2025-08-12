package com.example.data.auth.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.example.data.auth.google.GoogleCredentialDataSource
import com.patan.feature.login.data.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object GoogleSignInModule {

    @Provides
    @Singleton
    fun provideCredentialManager(
        @ApplicationContext context: Context
    ): CredentialManager = CredentialManager.create(context)

    @Provides
    @Singleton
    fun provideWebClientId(@ApplicationContext ctx: Context): () -> String = {
        ctx.getString(R.string.default_web_client_id)
    }

    @Provides
    @Singleton
    fun provideGoogleCredentialDataSource(
        cm: CredentialManager,
        webClientIdProvider: () -> String
    ): GoogleCredentialDataSource = GoogleCredentialDataSource(cm, webClientIdProvider)
}