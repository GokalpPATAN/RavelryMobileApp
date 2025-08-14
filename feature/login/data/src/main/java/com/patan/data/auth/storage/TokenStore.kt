package com.patan.data.auth.storage

import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import com.patan.data.auth.ravelry.TokenResponse

private val Context.authDataStore by preferencesDataStore(name = "auth")
private const val SKEW_SECONDS = 30L


@Singleton
class TokenStore @Inject constructor(
    @ApplicationContext private val app: Context
) {

    private object Keys {
        val access  = stringPreferencesKey("access")
        val refresh = stringPreferencesKey("refresh")
        val expiry  = longPreferencesKey("expiryEpochSec")
    }


    suspend fun save(t: TokenResponse) {
        val nowSec = System.currentTimeMillis() / 1000
        val expiresAt = nowSec + t.expiresIn - SKEW_SECONDS
        app.authDataStore.edit { p ->
            p[Keys.access] = t.accessToken
            t.refreshToken?.let { p[Keys.refresh] = it }
            p[Keys.expiry] = expiresAt // ✅ yeni
        }
    }

    suspend fun clear() { app.authDataStore.edit { it.clear() } }

    suspend fun getAccess(): String? =
        app.authDataStore.data.map { it[Keys.access] }.first()

    suspend fun getRefresh(): String? =
        app.authDataStore.data.map { it[Keys.refresh] }.first()

    suspend fun isExpired(): Boolean {
        val now = System.currentTimeMillis() / 1000L
        val exp = app.authDataStore.data.map { it[Keys.expiry] ?: 0L }.first()
        return exp <= now
    }
}