package com.patan.data.auth.settings

import android.content.Context
import com.patan.feature.login.data.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RavelrySettingsRes @Inject constructor(
    @ApplicationContext private val context: Context
) : RavelrySettings {

    override val clientId: String
        get() = context.getString(R.string.ravelry_client_id)

    override val clientSecret: String
        get() = context.getString(R.string.ravelry_client_secret)

    override val redirectUri: String
        get() = context.getString(R.string.ravelry_redirect_uri)

    override val scopes: String
        get() = context.getString(R.string.ravelry_scopes)

    override val authUrl: String
        get() = context.getString(R.string.ravelry_auth_url)

    override val tokenUrl: String
        get() = context.getString(R.string.ravelry_token_url)
}