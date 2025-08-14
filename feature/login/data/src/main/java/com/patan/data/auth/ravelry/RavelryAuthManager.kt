package com.patan.data.auth.ravelry

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.net.toUri

@Singleton
class RavelryAuthManager @Inject constructor(
    @ApplicationContext private val app: Context
){
    private val state = UUID.randomUUID().toString()
    private val _codeFlow = MutableSharedFlow<Result<String>>(replay = 0)
    val codeFlow: Flow<Result<String>> = _codeFlow

    fun startLogin() {
        val scope = BuildConfig.RAVELRY_SCOPES // "profile-only offline" önerilir
        val authUrl = "https://www.ravelry.com/oauth2/auth".toUri().buildUpon()
            .appendQueryParameter("client_id", BuildConfig.RAVELRY_CLIENT_ID)
            .appendQueryParameter("redirect_uri", BuildConfig.RAVELRY_REDIRECT_URI)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("scope", scope)
            .appendQueryParameter("state", state)
            .build()

        CustomTabsIntent.Builder().build().launchUrl(app, authUrl)
    }

    fun onAuthRedirect(uri: Uri) {
        val code = uri.getQueryParameter("code")
        val returnedState = uri.getQueryParameter("state")
        if (returnedState != state || code.isNullOrBlank()) {
            _codeFlow.tryEmit(Result.failure(IllegalStateException("Invalid state or code")))
        } else {
            _codeFlow.tryEmit(Result.success(code))
        }
    }
}