package com.filippo.either.login.domain

import com.filippo.either.common.domain.RequestError

sealed interface LoginState {
    object Idle : LoginState
    object Loading : LoginState
    object Success : LoginState
    data class Failure(val errors: List<LoginError>) : LoginState
}

sealed interface LoginError {
    object Username : LoginError
    sealed interface Password : LoginError {
        object Empty : Password
        object TooShort : Password
    }

    data class Request(val error: RequestError) : LoginError
}
