package com.patan.data.auth.ravelry

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.net.toUri
import com.patan.data.auth.settings.RavelrySettings

@Singleton
class RavelryAuthManager @Inject constructor(
    @ApplicationContext private val app: Context,
    private val settings: RavelrySettings,
    private val tmp: LoginTempStore
){
    private val _codeFlow = MutableSharedFlow<Result<String>>(replay = 0)
    val codeFlow: Flow<Result<String>> = _codeFlow

    suspend fun startLogin() {
        val state = UUID.randomUUID().toString()
        tmp.saveState(state)

        val scope = normalizeScopes(settings.scopes)
        val authUrl = Uri.parse(settings.authUrl).buildUpon()
            .appendQueryParameter("client_id", settings.clientId.trimQuotes())
            .appendQueryParameter("redirect_uri", settings.redirectUri.trimQuotes())
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("scope", scope) // örn: "profile-only" ya da "offline"
            .appendQueryParameter("state", state)
            .build()

        android.util.Log.d("RavelryAuth", "AUTH = ${Uri.decode(authUrl.toString())}")

        val tabs = CustomTabsIntent.Builder().build().apply {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try { tabs.launchUrl(app, authUrl) }
        catch (_: android.content.ActivityNotFoundException) {
            app.startActivity(Intent(Intent.ACTION_VIEW, authUrl).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    fun onAuthRedirect(uri: Uri) {
        val code = uri.getQueryParameter("code")
        val returnedState = uri.getQueryParameter("state")
        val savedState = kotlinx.coroutines.runBlocking { tmp.readState() }
        android.util.Log.d("RavelryAuth", "onAuthRedirect state(saved=$savedState, returned=$returnedState)")

        if (savedState.isNullOrBlank() || savedState != returnedState || code.isNullOrBlank()) {
            _codeFlow.tryEmit(Result.failure(IllegalStateException("Invalid state or code")))
        } else {
            _codeFlow.tryEmit(Result.success(code))
        }
        kotlinx.coroutines.runBlocking { tmp.clear() } // tek atış
    }

    private fun normalizeScopes(raw: String) =
        raw.trim().trim('"', '\'').replace(",", " ").replace("+", " ")
            .split(' ').filter { it.isNotBlank() }.joinToString(" ")
    private fun String.trimQuotes() = trim().trim('"', '\'')
}