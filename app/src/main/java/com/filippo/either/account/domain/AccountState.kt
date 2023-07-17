package com.filippo.either.account.domain

import com.filippo.either.common.domain.RequestError

sealed interface AccountState {
    object Loading : AccountState
    data class Failure(val error: AccountError) : AccountState
    data class Success(val accountDetails: AccountDetails) : AccountState
}

sealed interface AccountError {
    object NotLoggedIn : AccountError
    data class Request(val error: RequestError) : AccountError
}
