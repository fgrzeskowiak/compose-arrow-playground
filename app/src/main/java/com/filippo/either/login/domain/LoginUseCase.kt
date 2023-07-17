package com.filippo.either.login.domain

import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(username: String, password: String) = either {
        val credentials = zipOrAccumulate(
            { Username.validate(username).bind() },
            { Password.validate(password).bind() },
            ::Credentials
        )
        loginRepository.login(credentials)
            .mapLeft { listOf(LoginError.Request(it)) }
            .bind()
    }
}
