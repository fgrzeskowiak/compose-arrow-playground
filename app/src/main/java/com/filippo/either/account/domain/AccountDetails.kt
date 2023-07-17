package com.filippo.either.account.domain

data class AccountDetails(
    val gravatarHash: GravatarHash,
    val id: Int,
    val includeAdult: Boolean,
    val name: String,
    val username: String
) {
    @JvmInline
    value class GravatarHash(val value: String)
}
