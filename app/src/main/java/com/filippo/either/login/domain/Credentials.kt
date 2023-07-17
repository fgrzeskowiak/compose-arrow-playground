package com.filippo.either.login.domain

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure

@JvmInline
value class Username private constructor(val value: String) {
    companion object {
        fun validate(username: String): Either<LoginError, Username> = either {
            ensure(username.isNotBlank()) { LoginError.Username }
            Username(username)
        }
    }
}

@JvmInline
value class Password private constructor(val value: String) {
    companion object {
        fun validate(password: String): Either<LoginError, Password> = either {
            ensure(password.isNotEmpty()) { LoginError.Password.Empty }
            ensure(password.length > 5) { LoginError.Password.TooShort }
            Password(password)
        }
    }
}

data class Credentials(val username: Username, val password: Password)
