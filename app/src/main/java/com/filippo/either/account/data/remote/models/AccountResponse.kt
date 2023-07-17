package com.filippo.either.account.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AccountResponse(
    val avatar: Avatar,
    val id: Int,
    @SerialName("include_adult")
    val includeAdult: Boolean,
    val name: String,
    val username: String
) {
    @Serializable
    data class Avatar(val gravatar: Gravatar) {
        @Serializable
        data class Gravatar(val hash: String)
    }
}
