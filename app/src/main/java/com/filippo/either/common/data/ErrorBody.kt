package com.filippo.either.common.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorBody(
    @SerialName("status_code") val statusCode: Int,
    @SerialName("status_message") val statusMessage: String,
    @SerialName("success") val success: Boolean,
)
