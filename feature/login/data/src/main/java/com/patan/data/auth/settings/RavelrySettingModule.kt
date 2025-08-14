package com.patan.data.auth.settings

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RavelrySettingsModule {
    @Binds
    @Singleton
    abstract fun bindRavelrySettings(impl: RavelrySettingsRes): RavelrySettings

}