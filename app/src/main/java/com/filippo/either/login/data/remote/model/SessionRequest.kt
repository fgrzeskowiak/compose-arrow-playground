package com.filippo.either.login.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionRequest(@SerialName("request_token") val token: RequestToken)
