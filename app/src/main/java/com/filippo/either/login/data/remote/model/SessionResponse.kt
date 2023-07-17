package com.filippo.either.login.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionResponse(@SerialName("session_id") val sessionId: SessionId)
