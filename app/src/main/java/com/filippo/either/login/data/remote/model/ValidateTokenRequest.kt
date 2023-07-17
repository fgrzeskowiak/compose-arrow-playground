package com.filippo.either.login.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
 data class ValidateTokenRequest(
    @SerialName("request_token") val requestToken: RequestToken,
    @SerialName("username") val username: String,
    @SerialName("password") val password: String
)
