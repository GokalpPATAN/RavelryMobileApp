package com.patan.data.auth.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.patan.data.auth.network.AuthInterceptor
import com.patan.data.auth.ravelry.RavelryApi
import com.patan.data.auth.ravelry.RavelryOAuthService
import com.patan.data.auth.settings.RavelrySettings
import com.patan.data.auth.storage.TokenStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RavelryNetworkModule {

    private const val BASE_URL = "https://www.ravelry.com/"
    private val contentType = "application/json".toMediaType()
    private val json = Json { ignoreUnknownKeys = true }

    // ---- Ortak logging ----
    @Provides @Singleton
    fun provideLogging(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

    // =========================
    // AUTHLESS (interceptor yok)
    // =========================
    @Provides @Singleton @Authless
    fun provideAuthlessOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // DEBUG’ta açık bırak
            })
            .build()

    @Provides @Singleton @Authless
    fun provideAuthlessRetrofit(@Authless client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://www.ravelry.com/")
            .client(client)
            .addConverterFactory(Json { ignoreUnknownKeys = true }
                .asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides @Singleton
    fun provideOAuth(@Authless retrofit: Retrofit): RavelryOAuthService =
        retrofit.create(RavelryOAuthService::class.java)

    // =========================
    // AUTHED (interceptor var)
    // =========================
    @Provides @Singleton
    fun provideAuthInterceptor(
        tokenStore: TokenStore,
        settings: RavelrySettings,
        oauth: RavelryOAuthService     // AUTHLESS'tan geldiği için döngü olmaz
    ): AuthInterceptor = AuthInterceptor(tokenStore, settings, oauth)

    @Provides @Singleton @Authed
    fun provideAuthedOkHttp(
        auth: AuthInterceptor,
        logging: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(auth)
        .addInterceptor(logging)
        .build()

    @Provides @Singleton
    fun provideRetrofit(
        @Authed client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    @Provides @Singleton
    fun provideApi(
        retrofit: Retrofit
    ): RavelryApi = retrofit.create(RavelryApi::class.java)
}