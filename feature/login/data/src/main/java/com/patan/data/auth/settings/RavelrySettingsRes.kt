package com.patan.data.auth.settings

import javax.inject.Inject

class RavelrySettingsImpl @Inject constructor() : RavelrySettings {
    override val clientId: String get() = BuildConfig.RAVELRY_CLIENT_ID
    override val clientSecret: String get() = BuildConfig.RAVELRY_CLIENT_SECRET
    override val redirectUri: String get() = BuildConfig.RAVELRY_REDIRECT_URI
    override val scopes: String get() = BuildConfig.RAVELRY_SCOPES
    override val authUrl: String get() = BuildConfig.RAVELRY_AUTH_URL
    override val tokenUrl: String get() = BuildConfig.RAVELRY_TOKEN_URL
}