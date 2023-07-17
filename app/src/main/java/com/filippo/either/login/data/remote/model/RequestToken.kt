package com.filippo.either.login.data.remote.model

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class RequestToken(val token: String)
