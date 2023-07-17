package com.filippo.either.account.data

import com.filippo.either.account.data.remote.models.AccountResponse
import com.filippo.either.account.domain.AccountDetails

fun AccountResponse.toModel() = AccountDetails(
    gravatarHash = AccountDetails.GravatarHash(avatar.gravatar.hash),
    id = id,
    includeAdult = includeAdult,
    name = name,
    username = username
)
