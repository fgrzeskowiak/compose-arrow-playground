package com.filippo.either.login.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
 data class TokenResponse(
    @SerialName("expires_at") val expiresAt: String,
    @SerialName("request_token") val requestToken: RequestToken
)
