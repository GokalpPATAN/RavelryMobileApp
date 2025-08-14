package com.patan.data.auth.ravelry

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET

interface RavelryApi {
    @GET("current_user.json")
    suspend fun currentUser(): CurrentUserResponse
}

@Serializable
data class CurrentUserResponse(
    @SerialName("user") val user: RUser
)
@Serializable
data class RUser(
    val id: Long,
    val username: String
)