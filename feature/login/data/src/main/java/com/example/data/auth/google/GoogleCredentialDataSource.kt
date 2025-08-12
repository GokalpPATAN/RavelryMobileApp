package com.example.data.auth.google

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.example.domain.auth.repository.GoogleIdTokenProvider
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import javax.inject.Inject


class GoogleCredentialDataSource @Inject constructor(
    private val credentialManager: CredentialManager,
    private val webClientIdProvider: () -> String
): GoogleIdTokenProvider {

    override suspend fun getIdToken(context: Context): String? {
        val activity = context.requireActivity()
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(webClientIdProvider())
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = credentialManager.getCredential(activity, request) // <-- activity ver
        val credential = GoogleIdTokenCredential.createFrom(result.credential.data)
        return credential.idToken
    }
}

private fun Context.requireActivity(): Activity {
    tailrec fun unwrap(c: Context): Activity? =
        when (c) { is Activity -> c; is ContextWrapper -> unwrap(c.baseContext); else -> null }
    return unwrap(this) ?: error("Activity context is required for CredentialManager")
}