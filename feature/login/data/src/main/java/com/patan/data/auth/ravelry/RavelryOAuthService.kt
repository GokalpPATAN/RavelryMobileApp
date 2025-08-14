package com.patan.data.auth.ravelry

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

import retrofit2.http.*

interface RavelryOAuthService {

    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun exchangeCode(
        @Header("Authorization") basic: String,
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String
    ): TokenResponse

    // ✅ Refresh akışı
    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun refreshAccessToken(
        @Header("Authorization") basic: String,
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("refresh_token") refreshToken: String
    ): TokenResponse
}
@Serializable
data class TokenResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String? = null,
    @SerialName("token_type") val tokenType: String = "Bearer",
    @SerialName("expires_in") val expiresIn: Long,
    val scope: String? = null
)