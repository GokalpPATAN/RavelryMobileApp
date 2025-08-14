package com.patan.data.auth.network

import com.patan.data.auth.ravelry.RavelryOAuthService
import com.patan.data.auth.settings.RavelrySettings
import com.patan.data.auth.storage.TokenStore
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Response
import android.util.Base64

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenStore: TokenStore,
    private val settings: RavelrySettings,
    private val oauth: RavelryOAuthService
) : Interceptor {

    private val mutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val access = runBlocking { tokenStore.getAccess() } // ok: Interceptor sync
        if (access.isNullOrBlank()) {
            return chain.proceed(request)
        }
        val authed = request.newBuilder()
            .header("Authorization", "Bearer $access")
            .build()
        return chain.proceed(authed)
    }

    private suspend fun refreshIfPossible(): Boolean = mutex.withLock {
        val refresh = tokenStore.getRefresh() ?: return false
        return try {
            val basic = "Basic " + Base64.encodeToString(
                "${settings.clientId}:${settings.clientSecret}".toByteArray(),
                Base64.NO_WRAP
            )
            val t = oauth.refreshAccessToken(basic = basic, refreshToken = refresh)
            tokenStore.save(t)
            true
        } catch (_: Throwable) {
            tokenStore.clear()
            false
        }
    }
}