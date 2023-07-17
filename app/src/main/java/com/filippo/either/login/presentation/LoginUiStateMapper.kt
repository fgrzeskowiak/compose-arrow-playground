package com.filippo.either.login.presentation

import arrow.core.Either
import com.filippo.either.R
import com.filippo.either.common.data.TextResource
import com.filippo.either.common.presentation.errorMessage
import com.filippo.either.login.domain.LoginError

fun Either<List<LoginError>, Unit>.toDisplayModel(): LoginDisplayModel {
    val errors = leftOrNull().orEmpty()
    return LoginDisplayModel(
        isSuccessful = isRight(),
        usernameError = errors.getUsernameError(),
        passwordError = errors.getPasswordErrorMessage(),
        requestError = errors.getRequestError()
    )
}

private fun List<LoginError>.getUsernameError() = firstInstanceOf<LoginError.Username>()
    ?.let { TextResource.fromStringRes(R.string.username_error) }

private fun List<LoginError>.getPasswordErrorMessage() = firstInstanceOf<LoginError.Password>()
    ?.let {
        TextResource.fromStringRes(
            when (it) {
                is LoginError.Password.Empty -> R.string.password_empty_error
                is LoginError.Password.TooShort -> R.string.password_too_short_error
            }
        )
    }

private fun List<LoginError>.getRequestError() =
    firstInstanceOf<LoginError.Request>()?.error?.errorMessage


private inline fun <reified Subtype> List<*>.firstInstanceOf(): Subtype? {
    for (element in this) if (element is Subtype) return element
    return null
}
